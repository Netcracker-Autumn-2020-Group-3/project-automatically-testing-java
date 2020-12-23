package ua.netcracker.group3.automaticallytesting.dao;
import ua.netcracker.group3.automaticallytesting.dto.UserCountDto;
import ua.netcracker.group3.automaticallytesting.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User findUserByEmail(String email);

    String getEmail(Long id);

    void saveUser(User user);

    Optional<User> findUserById(long id);

    List<User> getUsersPageSorted(String orderByLimitOffsetWithValues, String isEnabledFiltering, String name, String surname, String email, String role);

    void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id);

    Integer countUsers();

    void updateUserPassword(String email, String password);

    void updateUserSettings(User user);

    Long getUserIdByEmail(String email);

    Integer countUsersSearch(String enabledSql, String name, String surname, String email, String formFilter);

    UserCountDto countOfUsersByRole();

    Boolean checkIfEmailExists(String email);
}
