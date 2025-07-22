import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;


public class Hotel {

    private static String url = "jdbc:mysql://localhost:3306/hotel";
    private static String username = "root";
    private static String password = "Deepu@2109";
    public static void main(String[] args) throws SQLException,Exception {

               try {
                                        Class.forName("com.mysql.cj.jdbc.Driver");

               } catch (Exception e) {
                
               }

                     
                        Connection conn = DriverManager.getConnection(url, username, password);
                     

                    Scanner sc=new Scanner(System.in);





                      
              while(true){

                System.out.println("HOTEL RESERVATION SYSTEM\n");

                System.out.println("1.Add Reservation");
                 System.out.println("2.View Reservation");
                  System.out.println("3.Get Room");
                   System.out.println("4.Update Reservation");
                    System.out.println("5.Delete Reservation");
                    System.out.println("6.Exit\n");
                       
                     System.out.println("Select the option");

                int option=sc.nextInt();
                 
                switch (option){
                    case 1:add_reservation(conn,sc);
                        
                        break;
                        case 2:view_reservation(conn);
                        
                        break;
                       
                        case 3: get_room(conn,sc);
                        
                        break;
                        case 4:update_reservation(conn,sc);
                        
                        break;
                        case 5:delete_reservation(conn,sc);
                        
                        break;
                        case 6:
                         exit();

                         

                        
                        return;
                        
                
                    default:
                    System.out.println("Invalid Choice");
                        break;
                }
            }
        }
    
                public static void add_reservation(Connection conn,Scanner sc) throws SQLException
                {
                  sc.nextLine();
                   System.out.println("enter name");
                String name=sc.next();
               System.out.println("enter room no");
               int room_no=sc.nextInt();
               sc.nextLine();
               System.out.println("enter contact numbner");
               String contact_number=sc.nextLine();

               String sql = "insert into reservation (name,room_no,contact_number) values(?,?,?)";

                PreparedStatement stmt =conn.prepareStatement(sql);

               stmt.setString(1,name);
               stmt.setInt(2,room_no);
               stmt.setString(3,contact_number);
               
           

             int affectedrow = stmt.executeUpdate();

             if(affectedrow>0)
                 System.out.println("Reservation is successful");
                 else {
                                   System.out.println("Reservation is unsuccessful");
            
                 }
                }
               
                public static void view_reservation(Connection conn) throws SQLException
                {

                    String sql = "select * from reservation";

                     PreparedStatement stmt =conn.prepareStatement(sql);

                   ResultSet rs = stmt.executeQuery();

                   System.out.println("Current Reservation");

                   while(rs.next()){

                  int reservation_id= rs.getInt("reservation_id");
                  int room_no = rs.getInt("room_no");
                  String name = rs.getString("name");
                  String contact_number = rs.getString("contact_number");
                  String reservation_date = rs.getString("reservation_date");
                   System.out.println("ID: " + reservation_id + ", Name: " + name + ", Room: " + room_no + ", Contact: " + contact_number + ", Date: " + reservation_date);

                   }  
                   }

                 public static void get_room(Connection conn,Scanner sc) throws SQLException
                 {


                    System.out.println("enter reservation id");
                    int reservation_id=sc.nextInt();
                    sc.nextLine();
                    System.out.println("enter name");
                    String name=sc.nextLine();


                    String sql = "select room_no from reservation where reservation_id=? and name=?";
                      PreparedStatement stmt =conn.prepareStatement(sql);
                      stmt.setInt(1, reservation_id);
                      stmt.setString(2,name);
                   ResultSet rs=stmt.executeQuery();
                   
                    if(rs.next()){
                       int room_no = rs.getInt("room_no");
                       System.out.println("room number:"+room_no+"guest name:"+name+"reservation id:"+reservation_id);
                    }
                    else{
                        System.out.println("reservation is not found in hotel");
                    }
                }

                public static void update_reservation(Connection conn,Scanner sc) throws SQLException
                 {

                    System.out.println("enter reservation id to update");
                    int reservationid=sc.nextInt();
                    sc.nextLine();

                    if(!reservationexists(conn,reservationid)){

                        System.out.println("reservation not exists ");
                        return;
                    }

                    else{
                    System.out.println("enter new name");
                    String newname=sc.nextLine();
                    System.out.println("enter new room no");
                    int newroom=sc.nextInt();
                    System.out.println("enter new contact no");
                    String newcontact=sc.nextLine();

                    String sql = "UPDATE reservation set name=?,room_no=?,contact_number=? where reservation_id=?";
                     PreparedStatement stmt =conn.prepareStatement(sql);
                     stmt.setString(1, newname);
                     stmt.setInt(2,newroom);
                     stmt.setString(3,newcontact);
                     stmt.setInt(4,reservationid);
                    int affectedrow=stmt.executeUpdate();

                    if(affectedrow>0){
                        System.out.println("updated succesfully");
                    }
                    else{
                        System.out.println("update failed");
                    }

                    }
                }
                    public static void delete_reservation(Connection conn,Scanner sc) throws SQLException
                     {

                        System.out.println("enter reservation id to delete");
                        int reservationid=sc.nextInt();

                        if(!reservationexists(conn,reservationid)){

                            System.out.println("reservation not exists......Can't delete");
                        }
                        else{
                            String sql = "DELETE from reservation where reservation_id=?";
                             PreparedStatement stmt =conn.prepareStatement(sql);
                             stmt.setInt(1, reservationid);

                            int affectedrow = stmt.executeUpdate();


                            if (affectedrow>0) {
                                System.out.println("reservation deleted successfully");
                                
                            }
                          else{
                            System.out.println("reservation deletion failed....try again");
                          }
                     
                        }
                    }
                        private static boolean reservationexists(Connection conn,int reservationid) throws SQLException
                        {
                         
                             String sql = "select reservation_id from reservation where reservation_id=?"; 

                              PreparedStatement stmt =conn.prepareStatement(sql);                   
                                     
                                      stmt.setInt(1, reservationid);
                                       ResultSet rs = stmt.executeQuery();


                          

                            return rs.next();

                    
                        }
                    public static void exit() throws Exception
                    {

                        System.out.print("exiting");
                        int i=7;
                        while(i!=0){
                            System.out.println(".");
                            Thread.sleep(1000);
                            i--;

                        }

                    }




                }

                

            

