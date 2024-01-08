import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Purveyor {
  private String _name;
  private String _address;
  private String _phoneNumber;
  public Purveyor(String name, String address, String phoneNumber) {
    set_name(name);
    set_address(address);
    set_phoneNumber(phoneNumber);
  }
  void set_name(String name) {
    _name = name;
  }
  void set_address(String address) {
    _address = address;
  }

  void set_phoneNumber(String phoneNumber) {
    _phoneNumber = phoneNumber;
  }
  
  void show_properties() {
    System.out.println("____________________");
    System.out.println("Name: " + _name + "\nAddress: " + _address + "\nPhone Number: " + _phoneNumber);
    System.out.println("____________________");
  }
  public void insertToDatabase()
  {
    try {
      Connection connection = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/chocolate_shop",
          "root",
          "qwerty123");
      String sqlInsert = "INSERT INTO purveyor (name, address,phone_number) VALUES (?, ?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
        preparedStatement.setString(1, _name);
        preparedStatement.setString(2, _address);
        preparedStatement.setString(3, _phoneNumber);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
          System.out.println("Added successfully!");
        } else {
          System.out.println("Failed to add.");
        }
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}