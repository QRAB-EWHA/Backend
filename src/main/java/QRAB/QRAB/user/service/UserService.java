package QRAB.QRAB.user.service;


import QRAB.QRAB.profile.domain.Profile;
import QRAB.QRAB.profile.repository.ProfileRepository;
import QRAB.QRAB.record.domain.Record;
import QRAB.QRAB.record.repository.RecordRepository;
import QRAB.QRAB.user.domain.Authority;
import QRAB.QRAB.user.domain.User;
import QRAB.QRAB.user.dto.MajorResponseDTO;
import QRAB.QRAB.user.dto.UserDTO;
import QRAB.QRAB.user.excepiton.DuplicateMemberException;
import QRAB.QRAB.user.repository.UserRepository;
import QRAB.QRAB.major.domain.Major;
import QRAB.QRAB.major.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MajorRepository majorRepository;

    private final ProfileRepository profileRepository;

    private final RecordRepository recordRepository;
    @Transactional
    public boolean checkEmailDuplicate(String email) {
        return userRepository.findByUsername(email).isPresent();
    }
    @Transactional
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));
    }


    @Transactional
    public UserDTO signup(UserDTO userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 사용 중인 이메일입니다.");
        }
        if(!userDto.getPassword().equals(userDto.getPasswordConfirm())){
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        List<Major> majorList = majorRepository.findAllByNameIn(new ArrayList<>(userDto.getMajorNames()));
        //List<Major> majorList = majorRepository.findAllById(userDto.getMajorIds());
        Set<Major> majorSet = new HashSet<>(majorList);

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER") //기본 유저는 ROLE_USER 권한만 가지고 있음
                .build();
        // userDTO 로부터 User 엔티티 생성
        User user = User.builder()
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .phoneNumber(userDto.getPhoneNumber())
                .password(passwordEncoder.encode(userDto.getPassword())) //비밀번호 암호화
                .authorities(Collections.singleton(authority))
                .activated(true) //활성화된 상태로 설정
                .majors(majorSet)
                .notification(0)//비공개로 설정
                .build();

        // Profile 엔티티 생성 (User와 연관)
        Profile profile = Profile.builder()
                .user(user) // User와 연결
                .nickname(userDto.getNickname()) // User와 동일한 값 설정
                .email(userDto.getUsername())    // User의 이메일을 Profile에도 사용
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber()) // User의 전화번호를 Profile에도 사용
                .build();
        // User와 Profile을 함께 저장
        user.setProfile(profile);

        User savedUser = userRepository.save(user);
        profileRepository.save(profile);
        return UserDTO.from(savedUser); //api 응답의 일관성유지와 User 엔티티 보호를 위해 UserDto 로 변환해서 반환
    }

    @Transactional
    public ResponseEntity<?> getLiberalArts(){
        List<Major> majorList = majorRepository.findByIdBetween(1L, 48L); //Major 객체 리스트 찾고

        List<MajorResponseDTO> majorResponseDTOS = majorList.stream()// Major 객체 리스트를 사용해서
                .map(MajorResponseDTO::fromEntity) // MajorResponseDTO 로 반환
                .collect(Collectors.toList());

        /*List<MajorResponseDTO> majorResponseDTOS = new ArrayList<>();
        for (Major major : majorList) {
            MajorResponseDTO dto = MajorResponseDTO.fromEntity(major);
            majorResponseDTOS.add(dto);
        }*/
        return ResponseEntity.ok(majorResponseDTOS);
    }

    @Transactional
    public ResponseEntity<?> getNaturalSciences(){
        List<Major> majorList = majorRepository.findByIdBetween(49L, 105L);

        List<MajorResponseDTO> majorResponseDTOS = majorList.stream()
                .map(MajorResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(majorResponseDTOS);
    }

    @Transactional
    public ResponseEntity<?> getEntertainmentSports(){
        List<Major> majorList = majorRepository.findByIdBetween(106L, 126L);

        List<MajorResponseDTO> majorResponseDTOS = majorList.stream()
                .map(MajorResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(majorResponseDTOS);
    }
    @Transactional
    public void logDailyLogin(String username){
        User user = userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(()-> new RuntimeException("Could not find user with email"));

        LocalDate today = LocalDate.now();
        boolean alreadyLoggedToday = recordRepository.existsByUserAndLoginDate(user, today);

        if(!alreadyLoggedToday){//오늘 처음 로그인함
            Record record = Record.builder()
                    .user(user)
                    .loginDate(today)
                    .build();
            recordRepository.save(record);
        }
        System.out.println("Saving record for user: " + user.getUsername() + " on " + today);
    }

    //이번달 총 학습일 수 계산
    @Transactional
    public int getTotalLearningDays(User user){
        LocalDate now = LocalDate.now();
        return (int) user.getRecords().stream()
                .filter(record -> record.getLoginDate().getMonth() == now.getMonth())
                .count();
    }

    //연속 학습일 수 계산
    @Transactional
    public int getConsecutiveLearningDays(User user){
        List<Record> records = user.getRecords().stream()
                .sorted(Comparator.comparing(Record::getLoginDate).reversed()).toList();
        int consecutiveDays = 0;
        LocalDate currentDate = LocalDate.now();

        for(Record record : records){
            if(record.getLoginDate().equals(currentDate)){
                consecutiveDays++;
                currentDate = currentDate.minusDays(1);
            }else{
                break;
            }
        }
        return consecutiveDays;
    }
}