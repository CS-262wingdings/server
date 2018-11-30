package edu.calvin.cs262.lab09;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.*;

/**
 * This Java annotation specifies the general configuration of the Google Cloud endpoint API.
 * The name and version are used in the URL: https://PROJECT_ID.appspot.com/monopoly/v1/ENDPOINT.
 * The namespace specifies the Java package in which to find the API implementation.
 * The issuers specifies boilerplate security features that we won't address in this course.
 *
 * You should configure the name and namespace appropriately.
 */
@Api(
     name = "monopoly", // change (name of app)
        version = "v1",
        namespace =
        @ApiNamespace(
                      ownerDomain = "lab09.cs262.calvin.edu", // change
                      ownerName = "lab09.cs262.calvin.edu", // change
                packagePath = ""
        ),
        issuers = {
                @ApiIssuer(
                        name = "firebase",
                        issuer = "https://securetoken.google.com/phonic-biplane-221307",
                        jwksUri =
                                "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system"
                                        + ".gserviceaccount.com"
                )
        }
)

/**
 * This class implements a RESTful service for the question table of the monopoly database.
 * Only the question table is supported, not the game or questiongame tables.
 *
 * You can test the GET endpoints using a standard browser or cURL.
 *
 * % curl --request GET \
 *    https://calvincs262-monopoly.appspot.com/monopoly/v1/questions
 *
 * % curl --request GET \
 *    https://calvincs262-monopoly.appspot.com/monopoly/v1/question/1
 *
 * You can test the full REST API using the following sequence of cURL commands (on Linux):
 * (Run get-questions between each command to see the results.)
 *
 * // Add a new question (probably as unique generated ID #4).
 * % curl --request POST \
 *    --header "Content-Type: application/json" \
 *    --data '{"name":"test name...", "emailAddress":"test email..."}' \
 *    https://calvincs262-monopoly.appspot.com/monopoly/v1/question
 *
 * // Edit the new question (assuming ID #4).
 * % curl --request PUT \
 *    --header "Content-Type: application/json" \
 *    --data '{"name":"new test name...", "emailAddress":"new test email..."}' \
 *    https://calvincs262-monopoly.appspot.com/monopoly/v1/question/4
 *
 * // Delete the new question (assuming ID #4).
 * % curl --request DELETE \
 *    https://calvincs262-monopoly.appspot.com/monopoly/v1/question/4
 *
 */
public class QuestionResources {

    /**
     * GET
     * This method gets the full list of questions from the Question table.
     *
     * @return JSON-formatted list of question records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path="question", httpMethod=GET)
    public List<Question> getQuestion() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Question> result = new ArrayList<Question>();
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectQuestion(statement);
            while (resultSet.next()) {
                Question p = new Question(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        java.sql.Timestamp.valueOf(resultSet.getString(3)),
                        Integer.parseInt(resultSet.getString(4))
                );
                result.add(p);
            }
        } catch (SQLException e) {
            throw(e);
        } finally {
            if (resultSet != null) { resultSet.close(); }
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }
        }
        return result;
    }

    /**
     * PUT
     * This method creates/updates an instance of Person with a given ID.
     * If the question doesn't exist, create a new question using the given field values.
     * If the question already exists, update the fields using the new question field values.
     * We do this because PUT is idempotent, meaning that running the same PUT several
     * times is the same as running it exactly once.
     * Any question ID value set in the passed question data is ignored.
     *
     * @param id     the ID for the question, assumed to be unique
     * @param question a JSON representation of the question; The id parameter overrides any id specified here.
     * @return new/updated question entity
     * @throws SQLException
     */
    // @ApiMethod(path="question/{id}", httpMethod=PUT)
    // public Question putQuestion(Question question, @Named("id") int id) throws SQLException {
    //     Connection connection = null;
    //     Statement statement = null;
    //     ResultSet resultSet = null;
    //     try {
    //         connection = DriverManager.getConnection(System.getProperty("cloudsql"));
    //         statement = connection.createStatement();
    //         question.setId(id);
    //         resultSet = selectQuestion(id, statement);
    //         if (resultSet.next()) {
    //             updateQuestion(question, statement);
    //         } else {
    //             insertQuestion(question, statement);
    //         }
    //     } catch (SQLException e) {
    //         throw (e);
    //     } finally {
    //         if (resultSet != null) { resultSet.close(); }
    //         if (statement != null) { statement.close(); }
    //         if (connection != null) { connection.close(); }
    //     }
    //     return question;
    // }

    /**
     * POST
     * This method creates an instance of Person with a new, unique ID
     * number. We do this because POST is not idempotent, meaning that running
     * the same POST several times creates multiple objects with unique IDs but
     * otherwise having the same field values.
     *
     * The method creates a new, unique ID by querying the question table for the
     * largest ID and adding 1 to that. Using a DB sequence would be a better solution.
     * This method creates an instance of Person with a new, unique ID.
     *
     * @param question a JSON representation of the question to be created
     * @return new question entity with a system-generated ID
     * @throws SQLException
     */
    @ApiMethod(path="question", httpMethod=POST)
    public Question postQuestion(Question question) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(ID) FROM Question");
            if (resultSet.next()) {
                question.setId(resultSet.getInt(1) + 1);
            } else {
                throw new RuntimeException("failed to find unique ID...");
            }
            insertQuestion(question, statement);
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (resultSet != null) { resultSet.close(); }
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }
        }
        return question;
    }

    /**
     * DELETE
     * This method deletes the instance of Person with a given ID, if it exists.
     * If the question with the given ID doesn't exist, SQL won't delete anything.
     * This makes DELETE idempotent.
     *
     * @param id     the ID for the question, assumed to be unique
     * @return the deleted question, if any
     * @throws SQLException
     */
    // @ApiMethod(path="question/{id}", httpMethod=DELETE)
    // public void deleteQuestion(@Named("id") int id) throws SQLException {
    //     Connection connection = null;
    //     Statement statement = null;
    //     try {
    //         connection = DriverManager.getConnection(System.getProperty("cloudsql"));
    //         statement = connection.createStatement();
    //         deleteQuestion(id, statement);
    //     } catch (SQLException e) {
    //         throw (e);
    //     } finally {
    //         if (statement != null) { statement.close(); }
    //         if (connection != null) { connection.close(); }
    //     }
    // }

    /** SQL Utility Functions *********************************************/



    /*
     * This function gets the question with the given id using the given JDBC statement.
     */
    // private ResultSet selectQuestion(int id, Statement statement) throws SQLException {
    //     return statement.executeQuery(
    //             String.format("SELECT * FROM Question WHERE id=%d", id)
    //     );
    // }

    /*
     * This function gets the question with the given id using the given JDBC statement.
     */
    private ResultSet selectQuestion(Statement statement) throws SQLException {
        return statement.executeQuery(
                "SELECT * FROM Question"
        );
    }

    /*
     * This function modifies the given question using the given JDBC statement.
     */
    // private void updateQuestion(Question question, Statement statement) throws SQLException {
    //     statement.executeUpdate(
    //             String.format("UPDATE Question SET contents ='%s', downloads=%d WHERE id=%d",
    //                     question.getContent(),
    //                     question.getDownloads(),
    //                     question.getId()
    //             )
    //     );
    // }

    /*
     * This function inserts the given question using the given JDBC statement.
     */
    private void insertQuestion(Question question, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO Question VALUES (%d, '%s', '%s', %d)",
                        question.getId(),
                        question.getContents(),
                        question.getTime().toString(),
                        question.getDownloads()
                )
        );
    }

    /*
     * This function gets the question with the given id using the given JDBC statement.
     */
    // private void deleteQuestion(int id, Statement statement) throws SQLException {
    //     statement.executeUpdate(
    //             String.format("DELETE FROM Question WHERE id=%d", id)
    //     );
    // }

    /*
     * This function returns a value literal suitable for an SQL INSERT/UPDATE command.
     * If the value is NULL, it returns an unquoted NULL, otherwise it returns the quoted value.
     */
    private String getValueStringOrNull(String value) {
        if (value == null) {
            return "NULL";
        } else {
            return "'" + value + "'";
        }
    }

}
