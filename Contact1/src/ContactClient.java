import java.sql.*;
import java.util.Scanner;

public class ContactClient {
    private static final String URL = "jdbc:mysql://localhost:3306/contacts?useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("已连接到数据库。");
            System.out.println("输入命令：");
            System.out.println("增加 姓名 地址 电话  - 增加联系人");
            System.out.println("更改 姓名 新地址 新电话  - 修改联系人信息");
            System.out.println("删除 姓名  - 删除联系人");
            System.out.println("查看  - 查看所有联系人");
            System.out.println("退出  - 退出程序");

            while (true) {
                System.out.print("> ");
                String inputLine = scanner.nextLine();
                if ("退出".equalsIgnoreCase(inputLine)) {
                    break;
                }
                executeCommand(conn, inputLine);
            }
        } catch (SQLException e) {
            System.err.println("数据库异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executeCommand(Connection conn, String inputLine) throws SQLException {
        String[] parts = inputLine.split(" ");
        String command = parts[0];

        switch (command) {
            case "增加":
                addContact(conn, parts);
                break;
            case "更改":
                modifyContact(conn, parts);
                break;
            case "删除":
                deleteContact(conn, parts);
                break;
            case "查看":
                viewContacts(conn);
                break;
            default:
                System.out.println("无效命令");
                break;
        }
    }

    private static void addContact(Connection conn, String[] parts) throws SQLException {
        if (parts.length != 4) {
            System.out.println("格式无效。使用格式：增加 姓名 地址 电话");
            return;
        }
        String name = parts[1];
        String address = parts[2];
        String phone = parts[3];

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO man (name, address, phone) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setInt(3, Integer.parseInt(phone)); // 假设电话是整数
            stmt.executeUpdate();
            System.out.println("联系人添加成功");
        }
    }

    private static void modifyContact(Connection conn, String[] parts) throws SQLException {
        if (parts.length != 4) {
            System.out.println("格式无效。使用格式：更改 姓名 新地址 新电话");
            return;
        }
        String name = parts[1];
        String address = parts[2];
        String phone = parts[3];

        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE man SET address = ?, phone = ? WHERE name = ?")) {
            stmt.setString(1, address);
            stmt.setInt(2, Integer.parseInt(phone)); // 假设电话是整数
            stmt.setString(3, name);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("联系人信息已更改");
            } else {
                System.out.println("未找到联系人");
            }
        }
    }

    private static void deleteContact(Connection conn, String[] parts) throws SQLException {
        if (parts.length != 2) {
            System.out.println("格式无效。使用格式：删除 姓名");
            return;
        }
        String name = parts[1];

        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM man WHERE name = ?")) {
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("联系人已删除");
            } else {
                System.out.println("未找到联系人");
            }
        }
    }

    private static void viewContacts(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM man")) {
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                int phone = rs.getInt("phone");

                // 检查联系人信息是否有效
                if (name != null && !name.isEmpty() && phone != 0) {
                    System.out.println(name + " " + address + " " + phone);
                }
            }
        }
    }

}
