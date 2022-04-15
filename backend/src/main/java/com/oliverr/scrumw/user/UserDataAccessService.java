package com.oliverr.scrumw.user;

import com.oliverr.scrumw.security.PasswordEncoder;
import com.oliverr.scrumw.util.TokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDataAccessService implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder encoder;
    private final TokenGenerator tokenGenerator;

    @Override
    public List<User> selectUsers() {
        var sql = "SELECT * FROM users;";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public int insertUser(User user) {
        var sql = """
                INSERT INTO users(username, email, password, token, reg_date)
                VALUES(?, ?, ?, ?, ?);
                """;
        String token = tokenGenerator.generateToken(128);
        String encryptedPassword = encoder.bCryptPasswordEncoder().encode(user.getPassword());
        return jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), encryptedPassword, token, LocalDate.now());
    }

    @Override
    public int deleteUser(int id) {
        var sql = """
                DELETE FROM users
                WHERE id = ?;
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> selectUserById(int id) {
        var sql = """
                SELECT *
                FROM users
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, new UserRowMapper(), id).stream().findFirst();
    }

    @Override
    public Optional<User> getUserByToken(String token) {
        var sql = """
                SELECT *
                FROM users
                WHERE token = ?;
                """;
        Optional<User> result = jdbcTemplate.query(sql, new UserRowMapper(), token).stream().findFirst();
        result.orElse(new User()).setPassword(null);
        return result;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        var sql = """
                SELECT *
                FROM users
                WHERE email = ?;
                """;
        return jdbcTemplate.query(sql, new UserRowMapper(), email).stream().findFirst();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        var sql = """
                SELECT *
                FROM users
                WHERE username = ?;
                """;
        return jdbcTemplate.query(sql, new UserRowMapper(), username).stream().findFirst();
    }

    @Override
    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        var sql = """
                SELECT *
                FROM users
                WHERE username = ?;
                """;
        return jdbcTemplate.query(sql, new UserRowMapper(), username)
                .stream()
                .filter(user -> encoder.bCryptPasswordEncoder().matches(password, user.getPassword()))
                .findFirst();
    }

    @Override
    public Optional<String> getTokenByUsername(String username) {
        var sql = """
                SELECT *
                FROM users
                WHERE username = ?;
                """;
        var result =  jdbcTemplate.query(sql, new UserRowMapper(), username).stream().findFirst();
        return result.map(User::getToken);
    }

}
