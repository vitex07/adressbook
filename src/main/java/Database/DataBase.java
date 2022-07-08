package Database;
import java.sql.*;


public class DataBase
{
    public static final String driver ="com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://localhost:3306/mydbtest?"+ "user=root&password=shizukou123&useUnicode=true&characterEncoding=UTF8";
    public static int Id = -1;
    private DataBase(){}
    public static Connection cnn=null;


    /*登录，查找user*/
    public static ResultSet getCustomer(String user,String pass)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            PreparedStatement ps = cnn.prepareStatement("select * from customer" + " where customerAccount=?" + " and customerPassword=?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, user);
            ps.setString(2, pass);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            return rs;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
        return null;
    }

    /*查找联系人*/
    public static ResultSet searchProduct(String words,int pattern)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            PreparedStatement ps;
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            if (pattern==1) {
                 ps = cnn.prepareStatement("\tSELECT\n" +
                                "\tcontact.*, \n" +
                                "\t`group`.*\n" +
                                "FROM\n" +
                                "\tcontact\n" +
                                "\tINNER JOIN\n" +
                                "\t`group`\n" +
                                "\tON \n" +
                                "\t\tcontact.classId = `group`.classId\n" +
                                "WHERE\n" +
                                "\t(contact.`name` LIKE ? OR\n" +
                                "\tcontact.stock LIKE ? )OR\n" +
                                "\t`group`.detail LIKE ?\n" +
                                "ORDER BY\n" +
                                "\tcontact.`name` ASC",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            }
            else
            {
                ps = cnn.prepareStatement("\tSELECT\n" +
                                "\tcontact.*, \n" +
                                "\t`group`.*\n" +
                                "FROM\n" +
                                "\tcontact\n" +
                                "\tINNER JOIN\n" +
                                "\t`group`\n" +
                                "\tON \n" +
                                "\t\tcontact.classId = `group`.classId\n" +
                                "WHERE\n" +
                                "\t(contact.`name` LIKE ? OR\n" +
                                "\tcontact.stock LIKE ? )OR\n" +
                                "\t`group`.detail LIKE ?\n" +
                                "ORDER BY\n" +
                                "\tcontact.`name` DESC",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            }
            ps.setString(1, "%"+words+"%");
            try
            {
                ps.setString(2,"%"+words);
            }
            catch (NumberFormatException nfe)
            {
                ps.setInt(2,1111111415);
            }
            ps.setString(3,"%"+words+"%");
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            return rs;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
        return null;
    }

    public static ResultSet getGroup()
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            PreparedStatement ps = cnn.prepareStatement("\tSELECT\n" +
                            "\t`group`.*\n" +
                            "FROM\n" +
                            "\t`group`\n",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            return rs;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
        return null;
    }

    /*按名称查找组*/
    public static ResultSet getContact(String groupName)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            PreparedStatement ps;
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            if (groupName.equals("all"))
            {
                ps = cnn.prepareStatement("SELECT\n" +
                                "\tcontact.*, \n" +
                                "\t`group`.*\n" +
                                "FROM\n" +
                                "\tcontact\n" +
                                "\tINNER JOIN\n" +
                                "\t`group`\n" +
                                "\tON \n" +
                                "\t\tcontact.classId = `group`.classId\n",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            }
            else
            {
                ps = cnn.prepareStatement("SELECT\n" +
                                "\tcontact.*, \n" +
                                "\t`group`.*\n" +
                                "FROM\n" +
                                "\tcontact\n" +
                                "\tINNER JOIN\n" +
                                "\t`group`\n" +
                                "\tON \n" +
                                "\t\tcontact.classId = `group`.classId\n" +
                                "WHERE\n" +
                                "\t`group`.detail = ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, groupName);
            }
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            return rs;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
        return null;
    }

    /*注册*/
    public static void addCustomer(String acc,String pass,String ema,String nick)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            PreparedStatement ps = cnn.prepareStatement("INSERT into customer(customer.customerAccount, customer.customerPassword, customer.customerMail, customer.nickName) " +
                    "VALUES (?,?,?,?);");
            ps.setString(1, acc);
            ps.setString(2,pass);
            ps.setString(3,ema);
            ps.setString(4,nick);
            System.out.println(ps);
            ps.executeUpdate();
        }
        catch (ClassNotFoundException e)
        {
            //e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
    }

    /*关闭连接*/
    public static void closeConnection()
    {
        try
        {
            DataBase.cnn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("close connect failed");
        }
    }

    /*增加联系人*/
    public static void insertProduct(String name,String introduction,String tel,String path,String sex,int classId)
    {
         try
         {
             Class.forName(driver);
             System.out.println("sql driver success");
             DataBase.cnn = DriverManager.getConnection(url);
             if (!DataBase.cnn.isClosed())
             {
                 System.out.println("database connection success");
             }
             PreparedStatement ps = cnn.prepareStatement("\tINSERT into contact(\n" +
                     "\tcontact.`name`, \n" +
                     "\tcontact.introduction, \n" +
                     "\tcontact.stock, \n" +
                     "\tcontact.showPicture, \n" +
                     "\tcontact.shelfTime, \n" +
                     "\tcontact.sex, \n" +
                     "\tcontact.classId)\n" +
                     "\tVALUES(?,?,?,?,CURDATE(),?,?)");
             ps.setString(1,name );
             ps.setString(2,introduction);
             ps.setString(3,tel);
             ps.setString(4,path);
             ps.setString(5,sex);
             ps.setInt(6,classId);
             System.out.println(ps);
             ps.executeUpdate();
         }
         catch (ClassNotFoundException e)
         {
             e.printStackTrace();
             System.out.println("sql driver fail");
         }
         catch (SQLException e)
         {
             e.printStackTrace();
             System.out.println("database connect failed");
         }
     }

    /*增加新组*/
    public static void addGroup(String detail)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            PreparedStatement ps = cnn.prepareStatement("\tINSERT into `group`(detail)\n" +
                    "\tVALUES(?)");
            ps.setString(1,detail);
            System.out.println(ps);
            ps.executeUpdate();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
    }

    /*删除组*/
    public static void deleteGroup(String detail)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            PreparedStatement ps = cnn.prepareStatement("\tDELETE from `group`\n" +
                    "\tWHERE detail=?");
            ps.setString(1,detail);
            System.out.println(ps);
            ps.executeUpdate();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
    }

    /*删除联系人*/
    public static void deleteContact(String tel)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            PreparedStatement ps = cnn.prepareStatement("DELETE FROM contact\n" +
                    "WHERE stock =?");
            ps.setString(1,tel);
            System.out.println(ps);
            ps.executeUpdate();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
    }

    /*更新联系人*/
    public static void updateContact(String name,String tel1,int Id,String sex,int classId)
    {
        try
        {
            Class.forName(driver);
            System.out.println("sql driver success");
            DataBase.cnn = DriverManager.getConnection(url);
            if (!DataBase.cnn.isClosed())
            {
                System.out.println("database connection success");
            }
            PreparedStatement ps = cnn.prepareStatement("UPDATE contact \n" +
                    "set `name`=?,stock = ?,sex=?,classId=?\n" +
                    "WHERE Id=?;");
            ps.setString(1,name);
            ps.setString(2,tel1);
            ps.setString(3,sex);
            ps.setInt(4,classId);
            ps.setInt(5,Id);
            System.out.println(ps);
            ps.executeUpdate();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("sql driver fail");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("database connect failed");
        }
    }
}
