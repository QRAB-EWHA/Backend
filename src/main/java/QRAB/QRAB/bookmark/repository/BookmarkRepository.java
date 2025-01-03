package QRAB.QRAB.bookmark.repository;

import QRAB.QRAB.bookmark.domain.Bookmark;
import QRAB.QRAB.bookmark.dto.BookmarkedNoteResponseDTO;
import QRAB.QRAB.bookmark.dto.BookmarkedQuizResponseDTO;
import QRAB.QRAB.user.domain.User;
import QRAB.QRAB.note.domain.Note;
import QRAB.QRAB.quiz.domain.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByUserAndQuiz(User user, Quiz quiz);

    @Query("SELECT new QRAB.QRAB.bookmark.dto.BookmarkedNoteResponseDTO(" +
            "n.id, n.title, COUNT(b.bookmarkId), n.quizGenerationCount) " +
            "FROM Bookmark b " +
            "JOIN b.quiz q " +
            "JOIN q.quizSet qs " +
            "JOIN qs.note n " +
            "WHERE b.user.userId = :userId " +
            "GROUP BY n.id, n.title, n.quizGenerationCount " +
            "HAVING COUNT(b.bookmarkId) > 0 " +
            "ORDER BY n.id")
    Page<BookmarkedNoteResponseDTO> findBookmarkedNotesWithCounts(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT n.id as noteId, n.title as title, " +
            "q.quizId as quizId, qs.quizSetId as quizSetId, " +
            "q.question as question, q.choices as choices, " +
            "qa.selectedAnswer as userAnswer, q.correctAnswer as correctAnswer, " +
            "qr.takenAt as solvedAt " +
            "FROM Bookmark b " +
            "JOIN b.quiz q " +
            "JOIN q.quizSet qs " +
            "JOIN qs.note n " +
            "LEFT JOIN QuizAnswer qa ON qa.quiz.quizId = q.quizId AND qa.quizSet.quizSetId = qs.quizSetId " +
            "LEFT JOIN QuizResult qr ON qr.resultId = qa.quizResult.resultId " +
            "WHERE n.id = :noteId AND b.user.userId = :userId")
    List<Object[]> findBookmarkedQuizzesByNoteId(@Param("noteId") Long noteId, @Param("userId") Long userId);

    @Query("SELECT n.title FROM Note n WHERE n.id = :noteId")
    String findNoteTitleById(@Param("noteId") Long noteId);
}
