package repository;

import com.home.notificationservice.entity.Task;
import com.home.notificationservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    UserEmailInfo findByPublicId(UUID publicId);

    List<Task> findTasksByUserId(String userId);
}
