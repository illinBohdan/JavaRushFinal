package com.javarush.javarushfinal.repository.user;

import org.springframework.stereotype.Repository;

import java.sql.*;
@Repository
public class UserRepositoryWithJDBC {

    public boolean existByUserNameJDBC(String userName)  {
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/auto_parts", "root", "1234");

        PreparedStatement statement = connection
                .prepareStatement("SELECT user_name FROM users where user_name = ?")) {

            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();
            boolean hasResult = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return hasResult;
        }catch (SQLException e){
            System.err.println("!!!ПОМИЛКА SQL!!!  " + e.getMessage());
        }
        return false;
    }
}
