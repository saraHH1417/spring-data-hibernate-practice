package guru.springframework.jdbc.repositories;

import guru.springframework.jdbc.domain.Book;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByTitle(String title);

    Book readByTitle(String title);


    @Nullable
    Book getByTitle(@Nullable String title);
}
