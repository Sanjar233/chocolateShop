import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Chocolate {
  private String _name;
  private String _type;
  private int _quantity;
  private double _price;
  private String _purveyor;
  private int _id;

  public Chocolate(String name, String type, int quantity, double price, String purveyor) {
    set_name(name);
    set_type(type);
    set_quantity(quantity);
    set_price(price);
    set_purveyor(purveyor);
    set_id();
  }

  void set_name(String name) {
    _name = name;
  }

  void set_type(String type) {
    _type = type;
  }

  void set_price(double price) {
    _price = price;
  }

  void set_quantity(int quantity) {
    _quantity = quantity;
  }

  void set_purveyor(String purveyor) {
    _purveyor = purveyor;
  }

  void set_id() {
    try {
      Connection connection = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/chocolate_shop",
          "root",
          "qwerty123");
      String query = "SELECT id FROM chocolate WHERE name = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, _name);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        _id = resultSet.getInt("id"); 
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  void show_properties() {
    System.out.println("____________________");
    System.out.println("Name: " + _name + "\nType: " + _type + "\nQuantity: " + _quantity + "\n Price: " + _price
        + "\nPurveyor: " + _purveyor + "\nID: " + _id);
    System.out.println("____________________");
  }

  public void insertToDatabase() {
    try {
      Connection connection = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/chocolate_shop",
          "root",
          "qwerty123");
      String sqlInsert = "INSERT INTO chocolate (name, type,quantity,price,purveyor) VALUES (?, ?,?,?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
        preparedStatement.setString(1, _name);
        preparedStatement.setString(2, _type);
        preparedStatement.setInt(3, _quantity);
        preparedStatement.setDouble(4, _price);
        preparedStatement.setString(5, _purveyor);
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
