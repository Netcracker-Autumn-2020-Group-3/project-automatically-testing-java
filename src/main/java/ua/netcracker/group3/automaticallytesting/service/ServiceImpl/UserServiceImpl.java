package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.dto.UserSearchDto;
import ua.netcracker.group3.automaticallytesting.model.Role;
import ua.netcracker.group3.automaticallytesting.dao.impl.UserDAOImpl;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.UserService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.util.Pagination;
import ua.netcracker.group3.automaticallytesting.util.PasswordResetToken;

import java.util.Arrays;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    UserDAO userDAO;
    Pagination pagination;
    private final PasswordEncoder passwordEncoder;
    private final List<String> USER_TABLE_FIELDS = Arrays.asList("id", "name", "surname", "role", "email", "is_enabled");

    @Autowired
    public UserServiceImpl(Pagination pagination, UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.pagination = pagination;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String getUserEmail(User user) {
        return userDAO.getEmail(user.getId());
    }

    @Override
    public void saveUser(User userRequest) {
        User user = buildUser(userRequest);
        userDAO.saveUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        return userDAO.getUserIdByEmail(email);
    }

    @Override
    public List<User> getUsers(UserSearchDto userSearchDto, Pageable pageable) {
        pagination.validate(pageable, USER_TABLE_FIELDS);
        return userDAO.getUsersPageSorted(pagination.formSqlPostgresPaginationPiece(pageable),
                userSearchDto.getOnlyEnabled() ? " and is_enabled=true " : "",
                userSearchDto.getName(),
                userSearchDto.getSurname(),
                userSearchDto.getEmail(),
                formFilter(userSearchDto.getRoles()));
    }

    private String formFilter(List<String> roles) {
        if (roles.isEmpty()) {
            return "%";
        }
        StringBuilder sb = new StringBuilder("(");
        roles.forEach(r -> sb.append(r).append("|"));
        return sb.deleteCharAt(sb.length() - 1).append(")").toString();
    }

    @Override
    public User getUserById(long id) {
        return userDAO.findUserById(id).orElseThrow(() -> new RuntimeException("user with id " + id + " not found"));
    }

    @Override
    public Integer countPages(Integer pageSize) {
        return pagination.countPages(userDAO.countUsers(), pageSize);
    }

    @Override
    public void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id) {
        userDAO.updateUserById(email, name, surname, role, is_enabled, id);
    }

    @Override
    public void updateUserPasswordByToken(String token, String password) throws Exception {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
            String resolvedToken = passwordResetToken.resolveToken(token);
            String email = passwordResetToken.getEmailFromResetToken(resolvedToken);
            userDAO.updateUserPassword(email, passwordEncoder.encode(password));
    }

    @Override
    public void updateUserSettings(User user) {
        userDAO.updateUserSettings(user);
    }

    @Override
    public void updateUserPassword(User user) {
        userDAO.updateUserPassword(user.getEmail(), passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public Integer countPagesSearch(UserSearchDto userSearchDto, Integer pageSize) {
        return pagination.countPages(userDAO.countUsersSearch(
                userSearchDto.getOnlyEnabled() ? " and is_enabled=true" : "",
                userSearchDto.getName(),
                userSearchDto.getSurname(),
                userSearchDto.getEmail(),
                formFilter(userSearchDto.getRoles())), pageSize);
    }

    @Override
    public Boolean checkIfEmailExists(String email) {
        return userDAO.checkIfEmailExists(email);
    }

    private User buildUser(User user) {
        return User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .surname(user.getSurname())
                .isEnabled(true)
                .role(user.getRole())
                .build();
    }
}
