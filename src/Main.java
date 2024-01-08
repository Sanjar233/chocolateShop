import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Statement;
// String name = reader.readLine();
// int userInput6 = Integer.parseInt(reader.readLine());
// double userInput7 = Double.parseDouble(reader.readLine());

public class Main {
    public static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static final Scanner scan = new Scanner(System.in);
    public static final String urlChoco = "jdbc:mysql://localhost:3306/chocolate_shop";
    public static final String user = "root";
    public static final String password = "qwerty123";

    public static void main(String[] args) throws Exception {
        List<Chocolate> chocolateList = new ArrayList<>();
        List<Purveyor> purveyorList = new ArrayList<>();
        while (true) {
            chocolateList = updatehocolateList();
            purveyorList = updatePurveyorList();
            clearConsole();
            System.out.println("1|Show chocolate list");//
            System.out.println("2|Change chocolate properties");
            System.out.println("3|Relenish supplies");
            System.out.println("4|Add new chocolate");//
            System.out.println("5|Show purveyors list");//
            System.out.println("6|Modify purveyors");//
            System.out.println("7|Add a transaction");
            System.out.println("8|Exit");
            int reply = scan.nextInt();
            clearConsole();
            if (reply == 1) {
                for (Chocolate e : chocolateList) {
                    e.show_properties();
                }
                set_pause();
            } else if (reply == 4) {
                
                int quantity = 0;
                System.out.println("Enter name");
                String name = reader.readLine();
                System.out.println("Enter type");
                String type = reader.readLine();
                clearConsole();
                System.out.println("Choose purveyor");
                for (Purveyor e : purveyorList) {
                    e.show_properties();
                }
                String purveyor = reader.readLine();
                System.out.print("Set price: ");
                double price = Double.parseDouble(reader.readLine());
                clearConsole();
                System.out
                        .println("Name: " + name + "\nType: " + type + "\nPurveyor: " + purveyor + "\nPrice: " + price);
                System.out.println("1|Confirm\n2|Back");
                reply = scan.nextInt();
                if (reply == 1) {
                    Chocolate temp = new Chocolate(name, type, quantity, price, purveyor);
                    temp.insertToDatabase();
                }
                else {
                    clearConsole();
                    continue;
                }
            }
            else if (reply == 5) {
                for (Purveyor e : purveyorList) {
                    e.show_properties();
                }
                set_pause();
            }
            else if (reply == 6) {
                System.out.println("1|Add new");
                System.out.println("2|Change properties for existing purveyor");
                System.out.println("3|Back");
                reply = scan.nextInt();
                clearConsole();
                if (reply == 1) {
                    System.out.println("Enter name");
                    String name = reader.readLine();
                    System.out.println("Enter address");
                    String address = reader.readLine();
                    System.out.println("Enter phone number");
                    String phone_number = reader.readLine();
                    clearConsole();
                    System.out
                            .println("Name: " + name + "\nType: " + address + "\nPhone Number: " + phone_number);
                    System.out.println("1|Confirm\n2|Back");
                    reply = scan.nextInt();
                    if (reply == 1) {
                        Purveyor temp = new Purveyor(name, address, phone_number);
                        temp.insertToDatabase();
                    } else {
                        clearConsole();
                        continue;
                    }
                }
                else if (reply == 2) {
                    
                }
                else if (reply == 3) {
                    clearConsole();
                    continue;
                }
            } else if (reply == 8) {
                System.exit(0);
            }
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void set_pause() {
        System.out.println("_________________");
        System.out.println("Press anything to continue");
        String pause = scan.next();
        clearConsole();
    }

    public static List<Chocolate> updatehocolateList() {
        List<Chocolate> chocolateList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(
                    urlChoco,
                    user,
                    password);
            String query = "SELECT * FROM chocolate";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                String purveyor = resultSet.getString("purveyor");
                Chocolate chocolate = new Chocolate(name, type, quantity, price, purveyor);
                chocolateList.add(chocolate);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (chocolateList);
    }

    public static List<Purveyor> updatePurveyorList() {
        List<Purveyor> purveyorList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(
                    urlChoco,
                    user,
                    password);
            String query = "SELECT * FROM purveyor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone_number = resultSet.getString("phone_number");
                Purveyor purveyor = new Purveyor(name, address, phone_number);
                purveyorList.add(purveyor);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (purveyorList);
    }
}
// System.out.println("Enter user id for change ");
//                     int idUser = scan.nextInt();
//                     System.out.println("Enter new name ");
//                     String newName = scan.next();
//                     System.out.println("Enter new age ");
//                     int newAge = scan.nextInt();
//                     try {
//                         // Prepare the SQL statement
//                         String sql = "UPDATE users SET name = ?, age = ? WHERE id_user = ?";
//                         try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                             // Set the parameter values
//                             statement.setString(1, newName);
//                             statement.setInt(2, newAge);
//                             statement.setInt(3, idUser);
//                             // Execute the update
//                             int rowsAffected = statement.executeUpdate();
//                             if (rowsAffected > 0) {
//                                 System.out.println("Row updated successfully.");
//                             } else {
//                                 System.out.println("Row not found or not updated.");
//                             }
//                         }
//                         // Close the connection
//                         connection.close();
//                     } catch (SQLException e) {
//                         e.printStackTrace();
//                     }