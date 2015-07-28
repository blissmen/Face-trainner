/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package General;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Harvey
 */
public class DatabaseHelper {

    @Nullable
    public Connection connection;
    @Nullable
    public ResultSet resultSet;
    @Nullable
    public ResultSetMetaData metaData;
    public int numberOfRows;
    @Nullable
    public Statement statement;
    public boolean connectedToDatabase;
    public ResultSet result;
    public ResultSetMetaData metadata;

    public DatabaseHelper() {

        this.numberOfRows = 0;
        this.statement = null;
        this.connectedToDatabase = false;
        this.resultSet = null;
        this.metaData = null;
        this.connection = null;

        createDB();//create the database if it does not exist
        // update database connection status
        connectedToDatabase = true;
        try {
            // statement.execute("delete from customers where name = 'Sonias Cousin Guen' ");
        } catch (Exception dd) {
            System.out.println(dd.getMessage());

        }
    }


    public final void setQuery(String query){
        try {
            // ensure database connection is available
            if (!connectedToDatabase) {
                throw new IllegalStateException("Not Connected to Database");
            }
            
            // specify query and execute it WATCH OUT: statement.executequery doesnot
//        run on queries that return number of rows such as inserts, so I use statement.execute()
            
            statement.execute(query);
            resultSet = statement.getResultSet();
            if (resultSet != null) {//a resultset is null if it is an update, count, and I think insert
                // determine number of rows in ResultSet
                resultSet.last(); // move to last row
                numberOfRows = resultSet.getRow(); // get row number
                metaData = (ResultSetMetaData) resultSet.getMetaData();
            } else {
                numberOfRows = 0;
            }
        } // end method setQuery
        catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getColumnCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine number of columns
        try {
            return metaData.getColumnCount();
        } // end try
        catch (SQLException sqlException) {
        } // end catch

        return 0; // if problems occur above, return 0 for number of columns
    } // end method getColumnCount

    // get name of a particular column in ResultSet
    public String getColumnName(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine column name
        try {
            return metaData.getColumnName(column + 1);
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return ""; // if problems, return empty string for column name
    } // end method getColumnName
    // return number of rows in ResultSet

    public int getRowCount() throws IllegalStateException {
        // ensure database connection is available

        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        return numberOfRows;
    } // end method getRowCount

    public Object getValueAt(int row, int column)
            throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // obtain a value at specified ResultSet row and column
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return ""; // if problems, return empty string object
    } // end method getValueAt

    @Nullable
    public Connection getConnection() {
        return connection;
    }

    @Nullable
    public Statement getStatement() {
        return statement;
    }

    @Nullable
    public ResultSet getResultSet() {
        return resultSet;
    }

    public void disconnectFromDatabase() {
        if (connectedToDatabase) {
// close Statement and Connection
            try {
                resultSet.close();
                statement.close();
                connection.close();

            } // end try
            catch (SQLException sqlException) {
            } // end catch
            finally // update database connection status
            {
                connectedToDatabase = false;
            } // end finally
        } // end if
    } // end method disconnectFromDatabase


    public int Query(String sql) {
        try {
            statement = (Statement) connection.createStatement();
            // System.out.println(sql);
            return statement.executeUpdate(sql);

        } catch (SQLException pp) {
            System.out.println(sql);
            System.out.println(pp.toString());
        }
        return 0;


    }

    @NotNull
    public ArrayList ExecuteQuery(String sql) throws SQLException {
        ArrayList resultSet = new ArrayList();
        try {
            if (connection != null) {
                statement = (Statement) connection.createStatement();
                result = statement.executeQuery(sql);
                metadata = (ResultSetMetaData) result.getMetaData();
                //  String[] res=null;
                int numcolls = metadata.getColumnCount();
                while (result.next()) {

                    for (int i = 1; i <= numcolls; i++) {

                        System.out.println(i + ":" + result.getObject(i));

                        resultSet.add(result.getObject(i));


                    }
                }
            }
        } catch (SQLException pp) {
            System.out.println(pp.getMessage());
            resultSet.add("");
        }

        if (resultSet.isEmpty())
            resultSet.add("");
        return resultSet;
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */


    public void createDB() {
       
            String url = "jdbc:mysql://localhost:3306/facialrecognitionsystem?zeroDateTimeBehavior=convertToNull";
           
            try {
                //Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager.getConnection(url, "root", "blissmen");
                statement = (Statement) connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                System.out.println("Connection success"+connection.toString());
            } catch (Exception e) {
                System.out.println(e.toString());
            }

    }
    }



