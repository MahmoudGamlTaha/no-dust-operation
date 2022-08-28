package operation;

import java.io.IOException;

import java.io.PrintStream;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Date;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBOPERATION {

    private final String op_con = "jdbc:postgresql://gdms.nodust-eg.com/NoDust_Operation";
   // private final String op_con = "jdbc:postgresql://localhost/Operation137";

    private final String dms_dev="jdbc:postgresql://gdms.nodust-eg.com/NoDust_DMS";
    private final String hub_dev="jdbc:postgresql://gdms.nodust-eg.com/NoDust_HUP";
    private final String user = "postgres";
    private final String password = "p0$tgr3$!!!";
    private final String dms_connection_string=dms_dev+"?user="+user+"&password="+password;
    private final String operation_connection_string =op_con+"?user="+user+"&password="+password;
        private final String hub_connection_string =hub_dev+"?user="+user+"&password="+password;
    public DBOPERATION() {
        super();
    }
    public Connection createOldOperationConnection() {
            Connection conn = null;
            InitialContext ic;
            try {
                ic = new InitialContext();
                javax.sql.DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/nodusttest");
                ds.setLoginTimeout(1800); // 10 minitues
                System.out.println("oracle connected");
                conn = ds.getConnection();
            
                //System.out.println(conn);
            } catch (NamingException e) {
                
            e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return conn;
                          
        }
    public static JSONArray convertToJSON(ResultSet resultSet)
                    throws Exception {
                JSONArray jsonArray = new JSONArray();
                
                while (resultSet.next()) {
                    JSONObject obj = new JSONObject();
                    int total_rows = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < total_rows; i++) {
                        Object b=resultSet.getObject(i + 1);
                        if(b==null){
                            obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                                    .toUpperCase(), JSONObject.NULL);
                        }
                        else{
                        obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                                .toUpperCase(), b);
                        }
                       
                    }
                    jsonArray.put(obj);
                }
                return jsonArray;
            }
     
        public Connection createOperationConnection() {
            Connection conn = null;
            InitialContext ic;
            try {
                ic = new InitialContext();
                javax.sql.DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/Operation");
                ds.setLoginTimeout(1800); // 10 minitues
                System.out.println("oracle connected");
                conn = ds.getConnection();
            
                //System.out.println(conn);
            } catch (NamingException e) {
                
            e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return conn;
        }
        /***********************************************************Create DMS_Dev Connection *******************************************************************/
        public Connection createDMSConnection() {
            Connection conn = null;
                                try {
                                   Class.forName("org.postgresql.Driver");
                                  Properties properties = new Properties();
                                   properties.put("connectTimeout", "600000");  // 10 min timeout
                               //  conn = DriverManager.getConnection(url, user, password);
                                 conn = DriverManager.getConnection(dms_connection_string,properties);
                                  System.out.println("Connected to the PostgreSQL server successfully.");
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                    e.printStackTrace();
                               } catch (ClassNotFoundException e) {
                     System.out.println(e.getMessage());
                   }
            
                    return conn;
        }
        public Connection createPayrollConnection() {
            Connection conn = null;
            InitialContext ic;
            try {
                ic = new InitialContext();
                javax.sql.DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/Payroll");
                ds.setLoginTimeout(1800); // 10 minitues
                System.out.println("oracle connected");
                conn = ds.getConnection();
            
                //System.out.println(conn);
            } catch (NamingException e) {
                
            e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return conn;
              
        }
        /****************************************************************************************************************************************************************/
       /******************************************************** Create HUB_Dev Connection ********************************************************************************************/
        public Connection createHUBConnection() {
            Connection conn = null;
                                try {
                                   Class.forName("org.postgresql.Driver");
                                  Properties properties = new Properties();
                                   properties.put("connectTimeout", "600000");  // 10 min timeout
                               //  conn = DriverManager.getConnection(url, user, password);
                                 conn = DriverManager.getConnection(hub_connection_string,properties);
                                  System.out.println("Connected to the PostgreSQL server successfully.");
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                    e.printStackTrace();
                               } catch (ClassNotFoundException e) {
                     System.out.println(e.getMessage());
                   }
            
                    return conn;
        }
    /**********************************************************************************************************************************************************************************************************/
    /****************************************************** Create Operation Connection **********************************************************************************************************************************/
        /*   public Connection createOperationConnection() {
            Connection conn = null;
            InitialContext ic;
            try {
                ic = new InitialContext();
                javax.sql.DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/Operation");
                ds.setLoginTimeout(600); // 10 minitues
                System.out.println("oracle connected");
                conn = ds.getConnection();
            
                //System.out.println(conn);
            } catch (NamingException e) {
                
            e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return conn;
        }*/
    /**********************************************************************************************************************************************************************************************************************/
    
    
    /***************************************************************************************************************************************************************************************************************************/
   /**********************************************************1. Upload Assignments From Operations To DMS ***************************************************************************************************************/    
        public boolean UploadReadyAssignments(int area_id,String assign_date,String driver_id) {
            Connection DMS_conn=createDMSConnection();
            Connection OP_Conn=createOperationConnection();
            if(DMS_conn!=null && OP_Conn!=null)
            {
            boolean prod_assignments=UploadReadyProductsAssignments(area_id, assign_date, driver_id,  DMS_conn ,  OP_Conn);
            if(prod_assignments) {
               // boolean collection_ass=UploadReadyCollectionAssignments(area_id,assign_date,driver_id,OP_Conn,DMS_conn);
               boolean collection_ass=true;
                if(collection_ass) {
                    String area_str=String.valueOf(area_id);
                    boolean area_insert=InsertAreaToDriver( driver_id, area_str, assign_date, DMS_conn);
                    if(area_insert) {
                        MarkAreaAsDispatched(area_id, assign_date, driver_id,   OP_Conn);
                        return true;
                        
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
            }
            
            return true;
        }
        /*************************************************************** 1.1.1  Upload Ready  Assignment OF Type (Product) & Products  *******************************************************************************************/
    public boolean UploadReadyProductsAssignments(int area_id,String assign_date,String driver_id, Connection dms_conn , Connection op_conn) {
        String op_query="";
        JSONArray assignments=new JSONArray();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String assignmnts_uploaded="";
        
        System.out.println("conn :"+op_conn);
        op_query="select assignment_id  ,card_no CARD_NO ,loc_confirmed LOC_CONFIRMED , area_id AREA_ID ,\n" + 
        "     client_name CLIENT_NAME ,order_delivery ORDER_DELIVERY ,priority PRIORITY ,\n" + 
        "remarks REMARKS ,phone_no PHONE_NO ,\n" + 
        "    home_no HOME_NO ,assign_id ASSIGN_ID ,floor_no FLOOR_NO , pilot_id PILOT_ID ,\n" + 
        "     pay_credit PAY_CREDIT \n" + 
        ",x X, street_name STREET_NAME , y Y , driv_id DRIV_ID  ,area_name AREA_NAME ,flat_no FLAT_NO ,mobile mobile ,assign_date ASSIGN_DATE \n" + 
        ", area2_name AREA2_NAME ,duration CHG_TYPE , region_name REGION_NAME ,city_name CITY_NAME ,address ADDRESS  ,mobile2 mobile_2\n" + 
        ",operation_comment OPERATION_COMMENT ,customer_type CUSTOMER_TYPE , data_comment DATA_COMMENT ,\n" + 
        "    spare_id SPARE_ID , assignment_type ASSIGNMENT_TYPE  , allow_credit CREDIT_ALLOWED , p_count B_COUNT\n" + 
        "   from assignment where created_state=1 and area_id="+area_id+" and driv_id='"+driver_id+"' and assign_date=TO_DATE('"+assign_date+"', 'YYYY-MM-DD') and assignment_type=1 ";
            try {
              //  System.out.println(op_query);
                pstmt = op_conn.prepareStatement(op_query);
                rs=pstmt.executeQuery();
                while(rs.next()) {
                    System.err.println("product");
                     JSONObject ass_obj=new JSONObject();
                    try {
                        ass_obj.put("ASSIGN_ID", rs.getString(rs.findColumn("assign_id")));
                        ass_obj.put( "DRIV_ID",rs.getString(rs.findColumn("DRIV_ID")));
                           ass_obj.put( "CARD_NO",rs.getLong(rs.findColumn("CARD_NO")));
                           ass_obj.put( "CLIENT_NAME",rs.getString(rs.findColumn("CLIENT_NAME")));
                           
                           ass_obj.put( "DATA_COMMENT",rs.getString(rs.findColumn("DATA_COMMENT")));
                           ass_obj.put("LOC_CONFIRMED",rs.getInt(rs.findColumn("LOC_CONFIRMED")));
                           ass_obj.put(  "OPERATION_COMMENT",rs.getString(rs.findColumn("OPERATION_COMMENT")));
                           String X=rs.getString(rs.findColumn("X"));
                           String Y=rs.getString(rs.findColumn("Y"));
                           if (X == null )
                           {
                               X = "0";
                               }
                           if (Y == null)
                           {
                               Y= "0";
                               }
                           if(X.contains("N")||Y.contains("N"))
                           {
                               ass_obj.put("X",0);
                               ass_obj.put("Y",0);
                           
                           }
                           else
                           {
                            ass_obj.put("X",Double.parseDouble(X));
                            ass_obj.put("Y",Double.parseDouble(Y));
                           }
                          ass_obj.put("PHONE_NO",rs.getString(rs.findColumn("PHONE_NO")));
                          ass_obj.put("RECIEPT_NO", rs.getString(rs.findColumn("assignment_id")));
                           ass_obj.put("PILOT_ID",rs.getString(rs.findColumn("PILOT_ID")));
                           ass_obj.put("ASSIGN_DATE", rs.getString(rs.findColumn("ASSIGN_DATE")));
                           ass_obj.put("PRIORITY",rs.getInt(rs.findColumn("PRIORITY")));
                           ass_obj.put("ORDER_DELIVERY", rs.getInt(rs.findColumn("ORDER_DELIVERY")));
                           ass_obj.put("REGION_NAME", rs.getString(rs.findColumn("REGION_NAME")));
                           ass_obj.put("STREET_NAME", rs.getString(rs.findColumn("STREET_NAME")));
                           ass_obj.put("HOME_NO", rs.getString(rs.findColumn("HOME_NO")));
                           ass_obj.put("FLOOR_NO", rs.getString(rs.findColumn("FLOOR_NO")));
                           ass_obj.put("FLAT_NO", rs.getString(rs.findColumn("FLAT_NO")));
                           ass_obj.put("REMARKS", rs.getString(rs.findColumn("REMARKS")));
                           ass_obj.put("CITY_NAME",rs.getString(rs.findColumn("CITY_NAME")));
                           ass_obj.put("CUSTOMER_TYPE",rs.getString(rs.findColumn("CUSTOMER_TYPE")));
                           ass_obj.put("AREA2_NAME",rs.getString(rs.findColumn("AREA2_NAME")));
                           ass_obj.put("ADDRESS",rs.getString(rs.findColumn("ADDRESS")));
                          // pstmt.setString(24, rs.getString(rs.findColumn("ADDRESS")));
                           String payCredit=rs.getString(rs.findColumn("PAY_CREDIT"));
                           if(payCredit==null)
                           ass_obj.put("PAY_CREDIT",false);
                           else
                           ass_obj.put("PAY_CREDIT",true);
                           ass_obj.put("AREA_ID",rs.getLong(rs.findColumn("AREA_ID")));
                           ass_obj.put("AREA_NAME", rs.getString(rs.findColumn("AREA_NAME")));
                           ass_obj.put("CHG_TYPE",rs.getInt(rs.findColumn("CHG_TYPE")));
                           ass_obj.put("mobile", rs.getString(rs.findColumn("mobile")));
                           ass_obj.put("mobile_2",rs.getString(rs.findColumn("mobile_2")));
                           ass_obj.put("SPARE_ID", rs.getObject(rs.findColumn("SPARE_ID")));
                           ass_obj.put("ASSIGNMENT_TYPE", rs.getObject(rs.findColumn("ASSIGNMENT_TYPE")));
                           if(rs.getString(rs.findColumn("CREDIT_ALLOWED"))!=null)
                           ass_obj.put("CREDIT_ALLOWED", rs.getObject(rs.findColumn("CREDIT_ALLOWED")));
                           else
                            ass_obj.put("CREDIT_ALLOWED", 0);
                           if(rs.getString(rs.findColumn("B_COUNT"))!=null)
                               ass_obj.put("B_COUNT", rs.getObject(rs.findColumn("B_COUNT")));
                           else
                               ass_obj.put("B_COUNT", 0);
                        ass_obj.put("OPERATION_ASS", rs.getString(rs.findColumn("assignment_id")));
                        JSONArray products=Get_Product_ForAssignments(rs.getInt(rs.findColumn("assignment_id")), rs.getInt("CHG_TYPE") , op_conn);
                       System.out.println(rs.getObject(rs.findColumn("CARD_NO")));
                        System.out.println(products);
                        ass_obj.put("PRODUCTS", products);
                           assignments.put(ass_obj);

                        assignmnts_uploaded+=rs.getInt(rs.findColumn("assignment_id"))+",";
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                   
                }
                System.out.println(assignments);

                if(assignmnts_uploaded.length()>0)
                {
                CallableStatement statement;
                try {
                    statement = dms_conn.prepareCall(" { call upload_operation_assignment( ?::json ) } ");
                    
                    statement.setObject(1, assignments.toString());

                    statement.execute();
                    statement.close();
                    assignmnts_uploaded=assignmnts_uploaded.substring(0,assignmnts_uploaded.length()-1);
                    String update_q="update assignment set created_state=2 where assignment_id in ("+assignmnts_uploaded+")";
                    pstmt=op_conn.prepareStatement(update_q);
                    pstmt.executeUpdate();
                    op_conn.commit();
                    return true;
                
                
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                }
                else {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }

    }
        public JSONArray Get_Product_ForAssignments(int assignment_id , int ch_type , Connection op_conn) {
            JSONArray products=new JSONArray();
            String op_query="select pcd.package_id , pcd.product_id , pcd.treatment_id , sum(pcd.quantity) quantity  , pcd.package_no , pcd.price  , pcd.delivery_type\n" + 
            "                  from product_contract_delivery pcd where assignment_id="+assignment_id+"\n" + 
            "                  group by pcd.package_id , pcd.product_id , pcd.treatment_id, pcd.package_no , pcd.price , pcd.delivery_type";
            ResultSet rs=null;
            PreparedStatement stmt=null;
            try {
                stmt = op_conn.prepareStatement(op_query);
                rs=stmt.executeQuery();
                while(rs.next()) {
                    JSONObject prod=new JSONObject();
                    try {
                        prod.put("package_id", rs.getString(rs.findColumn("package_id")));
                        prod.put("product_id", rs.getString(rs.findColumn("product_id")));
                        prod.put("treatment_id", rs.getString(rs.findColumn("treatment_id")));
                        prod.put("quantity", rs.getString(rs.findColumn("quantity")));
                        prod.put("quantity_replaced", 0);
                        prod.put("package_no", rs.getString(rs.findColumn("package_no")));
                        prod.put("price", rs.getString(rs.findColumn("price")));
                        prod.put("ch_type", rs.getString(rs.findColumn("delivery_type")));
                        prod.put("aid", 0);
    products.put(prod);
                        
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Assignemnt_id : "+assignment_id +"Products : "+ products);
            return products;
        }
        /*************************************************************** 1.2.1 Upload Ready  Assignment OF Type (Collections) & Invoices Data  *******************************************************************************************/
      public boolean UploadReadyCollectionAssignments(int area_id,String assign_date,String driver_id,Connection op_conn,Connection dms_conn)
      {
        String op_query="";
        JSONArray assignments=new JSONArray();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String assignmnts_uploaded="";
        op_query="select assignment_id  ,card_no CARD_NO , loc_confirmed LOC_CONFIRMED , area_id AREA_ID ,\n" + 
        "     client_name CLIENT_NAME ,order_delivery ORDER_DELIVERY ,priority PRIORITY ,\n" + 
        "remarks REMARKS ,phone_no PHONE_NO ,\n" + 
        "    home_no HOME_NO ,assign_id ASSIGN_ID ,floor_no FLOOR_NO , pilot_id PILOT_ID ,\n" + 
        "     pay_credit PAY_CREDIT \n" + 
        ",x X, street_name STREET_NAME , y Y , driv_id DRIV_ID  ,area_name AREA_NAME ,flat_no FLAT_NO ,mobile mobile ,assign_date ASSIGN_DATE \n" + 
        ", area2_name AREA2_NAME ,duration CHG_TYPE , region_name REGION_NAME ,city_name CITY_NAME ,address ADDRESS  ,mobile2 mobile_2\n" + 
        ",operation_comment OPERATION_COMMENT ,customer_type CUSTOMER_TYPE , data_comment DATA_COMMENT ,\n" + 
        "    spare_id SPARE_ID , assignment_type ASSIGNMENT_TYPE  , allow_credit CREDIT_ALLOWED , p_count B_COUNT\n" + 
        "   from assignment where created_state=1 and area_id="+area_id+" and driv_id='"+driver_id+"' and assign_date='"+assign_date+"' and assignment_type=4 ";
            try {
                
                pstmt = op_conn.prepareStatement(op_query);
                rs=pstmt.executeQuery();
                while(rs.next()) {
                     JSONObject ass_obj=new JSONObject();
                    try {
                        ass_obj.put("ASSIGN_ID", rs.getString(rs.findColumn("assign_id")));
                        ass_obj.put( "DRIV_ID",rs.getString(rs.findColumn("DRIV_ID")));
                           ass_obj.put( "CARD_NO",rs.getLong(rs.findColumn("CARD_NO")));
                           ass_obj.put( "CLIENT_NAME",rs.getString(rs.findColumn("CLIENT_NAME")));
                           
                           ass_obj.put( "DATA_COMMENT",rs.getString(rs.findColumn("DATA_COMMENT")));
                           ass_obj.put("LOC_CONFIRMED",rs.getInt(rs.findColumn("LOC_CONFIRMED")));
                           ass_obj.put(  "OPERATION_COMMENT",rs.getString(rs.findColumn("OPERATION_COMMENT")));
                           String X=rs.getString(rs.findColumn("X"));
                           String Y=rs.getString(rs.findColumn("Y"));
                           if (X == null )
                           {
                               X = "0";
                               }
                           if (Y == null)
                           {
                               Y= "0";
                               }
                           if(X.contains("N")||Y.contains("N"))
                           {
                               ass_obj.put("X",0);
                               ass_obj.put("Y",0);
                           
                           }
                           else
                           {
                            ass_obj.put("X",Double.parseDouble(X));
                            ass_obj.put("Y",Double.parseDouble(Y));
                           }
                          ass_obj.put("PHONE_NO",rs.getString(rs.findColumn("PHONE_NO")));
                          ass_obj.put("RECIEPT_NO", rs.getString(rs.findColumn("assignment_id")));
                           ass_obj.put("PILOT_ID",rs.getString(rs.findColumn("PILOT_ID")));
                           ass_obj.put("ASSIGN_DATE", rs.getString(rs.findColumn("ASSIGN_DATE")));
                           ass_obj.put("PRIORITY",rs.getInt(rs.findColumn("PRIORITY")));
                           ass_obj.put("ORDER_DELIVERY", rs.getInt(rs.findColumn("ORDER_DELIVERY")));
                           ass_obj.put("REGION_NAME", rs.getString(rs.findColumn("REGION_NAME")));
                           ass_obj.put("STREET_NAME", rs.getString(rs.findColumn("STREET_NAME")));
                           ass_obj.put("HOME_NO", rs.getString(rs.findColumn("HOME_NO")));
                           ass_obj.put("FLOOR_NO", rs.getString(rs.findColumn("FLOOR_NO")));
                           ass_obj.put("FLAT_NO", rs.getString(rs.findColumn("FLAT_NO")));
                           ass_obj.put("REMARKS", rs.getString(rs.findColumn("REMARKS")));
                           ass_obj.put("CITY_NAME",rs.getString(rs.findColumn("CITY_NAME")));
                           ass_obj.put("CUSTOMER_TYPE",rs.getString(rs.findColumn("CUSTOMER_TYPE")));
                           ass_obj.put("AREA2_NAME",rs.getString(rs.findColumn("AREA2_NAME")));
                           ass_obj.put("ADDRESS",rs.getString(rs.findColumn("ADDRESS")));
                          // pstmt.setString(24, rs.getString(rs.findColumn("ADDRESS")));
                           String payCredit=rs.getString(rs.findColumn("PAY_CREDIT"));
                           if(payCredit==null)
                           ass_obj.put("PAY_CREDIT",false);
                           else
                           ass_obj.put("PAY_CREDIT",true);
                           ass_obj.put("AREA_ID",rs.getLong(rs.findColumn("AREA_ID")));
                           ass_obj.put("AREA_NAME", rs.getString(rs.findColumn("AREA_NAME")));
                           ass_obj.put("CHG_TYPE",rs.getInt(rs.findColumn("CHG_TYPE")));
                           ass_obj.put("MOBILE", rs.getString(rs.findColumn("mobile")));
                           ass_obj.put("MOBILE2",rs.getString(rs.findColumn("mobile_2")));
                           ass_obj.put("SPARE_ID", rs.getObject(rs.findColumn("SPARE_ID")));
                           ass_obj.put("ASSIGNMENT_TYPE", rs.getObject(rs.findColumn("ASSIGNMENT_TYPE")));
                           if(rs.getString(rs.findColumn("CREDIT_ALLOWED"))!=null)
                           ass_obj.put("CREDIT_ALLOWED", rs.getObject(rs.findColumn("CREDIT_ALLOWED")));
                           else
                            ass_obj.put("CREDIT_ALLOWED", 0);
                           if(rs.getString(rs.findColumn("B_COUNT"))!=null)
                               ass_obj.put("B_COUNT", rs.getObject(rs.findColumn("B_COUNT")));
                           else
                               ass_obj.put("B_COUNT", 0);
                        JSONArray Collections=Get_Collections_ForAssignments(rs.getInt(rs.findColumn("assignment_id")), op_conn);
                        ass_obj.put("COLLECTION", Collections);
                           assignments.put(ass_obj);
                        assignmnts_uploaded+=rs.getInt(rs.findColumn("assignment_id"))+',';
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                   
                }
                if(assignmnts_uploaded.length()>0)
                {
                CallableStatement statement;
                try {
                    statement = dms_conn.prepareCall(" { call upload_collection_assignment( ?::json ) } ");
                    statement.setObject(1, assignments.toString());
                    statement.execute();
                    statement.close();
                    assignmnts_uploaded=assignmnts_uploaded.substring(0, assignmnts_uploaded.length()-1);
                    String update_query="update assignment set created_state=2 where assignment_id in ("+assignmnts_uploaded+")";
                    pstmt=op_conn.prepareStatement(update_query);
                    pstmt.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                
                }
                else {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
    }
        public JSONArray Get_Collections_ForAssignments(int assignment_id,Connection op_conn) {
            JSONArray collections=new JSONArray();
            String op_query="select col.invoice_no , col.amount , col.date invoice_date , col.paid_amount\n" + 
            "       from collection_contract_delivery col where assignment_id="+assignment_id;
            ResultSet rs=null;
            PreparedStatement stmt=null;
            try {
                stmt = op_conn.prepareStatement(op_query);
                rs=stmt.executeQuery();
                while(rs.next()) {
                    JSONObject col=new JSONObject();
                    try {
                        col.put("invoice_no", rs.getString(rs.findColumn("invoice_no")));
                        col.put("amount", rs.getString(rs.findColumn("amount")));
                        col.put("paid_amount", rs.getString(rs.findColumn("paid_amount")));
                        col.put("invoice_date", rs.getString(rs.findColumn("invoice_date")));
                        col.put("AID", 0);

            collections.put(col);

                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return collections;
        }
       /***************************************************************** 1.3 INSERT Area For Driver DMS *************************************************************************************************************************/
        public boolean InsertAreaToDriver(String DrivId,String AreaId,String AssignDate,Connection con){
            CallableStatement statement;
            try {
                statement = con.prepareCall(" { call insert_area_to_driver( ?::text,?::text ,?::text) } ");
                statement.setObject(1, DrivId);
                statement.setObject(2, AreaId);
                statement.setObject(3, AssignDate);
                statement.execute();
                statement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
return true;         

        }
       
       /************************************************************1.4 Mark Area As Dispatched and Uploaded To Cloud *********************************************************************************************************************************/
       public boolean MarkAreaAsDispatched(int area_id,String assign_date,String driver_id,  Connection op_conn) {
           String update_query="UPDATE DAILY_WORK SET AREA_STATUS=5 , cloud_flag=1 WHERE AREA_ID="+area_id+" and cover_date=TO_DATE('"+assign_date+"','YYYY-MM-DD') AND DRIVER_ID='"+driver_id+"'";
        try {
            PreparedStatement pstmt = op_conn.prepareStatement(update_query);
            pstmt.executeUpdate();
            op_conn.commit();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
       }
    /*****************************************************************************************************************************************************************************************************************************************/





    /*********************************************************** 2. Update Assignemnts On Cloud  *************************************************************************************************************************************************************/
       public boolean UpdateAssignmentsOnCloud() {
        Connection op_conn=createOperationConnection();
        Connection dms_conn=createDMSConnection();
        boolean products_update=Update_ProductAssignmnets(dms_conn, op_conn);
        if(products_update) {
           /*boolean coll_update=Update_CollectionAssignments(dms_conn, op_conn);
            if(coll_update) {
                return true;
            }
            else {
                return false;
            }*/ 
            return true;
        }
        else {
            return false;
        }
    }
    /*********************************************************** 2.1 Update Product Assignments Need Upload (updated =1 )**********************************************************************************************************************************/
    public boolean Update_ProductAssignmnets(Connection DMS_Conn, Connection op_Conn) {
        String op_query="";
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String updated_assignment_ids="";
        JSONArray products=new JSONArray();
        CallableStatement statement=null;
        JSONArray assignments=new JSONArray();
        op_query="select assignment_id , priority , operation_comment , duration,changed_state , card_no ,assign_id from assignment where updated=1 and assignment_type=1 ";
            try {
                pstmt = op_Conn.prepareStatement(op_query);
                rs=pstmt.executeQuery();
                while(rs.next()) {
                    JSONObject ass=new JSONObject();
                    try {
                        updated_assignment_ids+=rs.getInt(rs.findColumn("assignment_id"))+",";
                        ass.put("assignment_id", rs.getObject(rs.findColumn("assignment_id")));
                        ass.put("priority", rs.getObject(rs.findColumn("priority")));
                        ass.put("operation_comment",rs.getObject(rs.findColumn("operation_comment")));
                        ass.put("changed_state", rs.getObject(rs.findColumn("changed_state")));
                        ass.put("card_no", rs.getObject(rs.findColumn("card_no")));
                        ass.put("assign_id", rs.getObject(rs.findColumn("assign_id")));

                        products=Get_Product_ForAssignments(rs.getInt(rs.findColumn("assignment_id")), rs.getInt(rs.findColumn("duration")), op_Conn);
                        ass.put("PRODUCTS", products);
                        assignments.put(ass);
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                }
                if(updated_assignment_ids.length()>0) {
                    statement = DMS_Conn.prepareCall(" { call upload_updates_from_operation( ?::json) } ");
                    statement.setObject(1, assignments.toString());
                    statement.execute();
                    statement.close();
                    updated_assignment_ids=updated_assignment_ids.substring(0, updated_assignment_ids.length()-1);
                    boolean mark_ass_updated=MarkUpdatesAsUploaded(updated_assignment_ids, op_Conn);
                    if(mark_ass_updated) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return true;
                }
                    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
return true;
        }

    /************************************************************* 2.2 Update Collection Assignments Need Upload (updated=1) ********************************************************************************************************************************************/
     public boolean Update_CollectionAssignments(Connection DMS_Conn , Connection op_conn) {
        String op_query="";
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String updated_assignment_ids="";
        JSONArray collections=new JSONArray();
        CallableStatement statement=null;
        JSONArray assignments=new JSONArray();
        op_query="select assignment_id , priority , fu_note , duration,changed_state ,assign_id , card_no , performer from assignment where updated=1 and assignment_type=4 ";
            try {
                pstmt = op_conn.prepareStatement(op_query);
                rs=pstmt.executeQuery();
                while(rs.next()) {
                    JSONObject ass=new JSONObject();
                    try {
                        updated_assignment_ids+=rs.getInt(rs.findColumn("assignment_id"))+",";
                        ass.put("assign_id" , rs.getObject(rs.findColumn("assign_id")));
                        ass.put("card_no" , rs.getObject(rs.findColumn("card_no")));

                        ass.put("assignment_id", rs.getObject(rs.findColumn("assignment_id")));
                        ass.put("priority", rs.getObject(rs.findColumn("priority")));
                        ass.put("fu_note",rs.getObject(rs.findColumn("fu_note")));
                        ass.put("changed_state", rs.getObject(rs.findColumn("changed_state")));
                        ass.put("performer" , rs.getObject(rs.findColumn("performer")));
                        collections=Get_Collections_ForAssignments(rs.getInt(rs.findColumn("assignment_id")), op_conn);
                        ass.put("collections", collections);
                        assignments.put(ass);
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                }
                if(updated_assignment_ids.length()>0) {
                    statement = DMS_Conn.prepareCall(" { call upload_collection_updates_from_operations( ?::json) } ");
                    statement.setObject(1, assignments.toString());
                    statement.execute();
                    statement.close();
                    updated_assignment_ids=updated_assignment_ids.substring(0, updated_assignment_ids.length()-1);
                    boolean mark_ass_updated=MarkUpdatesAsUploaded(updated_assignment_ids, op_conn);
                    if(mark_ass_updated) {
                        pstmt.close();
                        
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return true;
                }
                    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
return true;
    }

    /************************************************************* 2.3  Mark Updated Assignments As Uploaded On Operation ***********************************************************************************************************************************************/
    public boolean MarkUpdatesAsUploaded(String ids , Connection op_conn) {
        String op_query="";
        PreparedStatement pstmt=null;
        op_query="update assignment set updated=0 where assignment_id in ("+ids+")";
            try {
                pstmt = op_conn.prepareStatement(op_query);
                pstmt.executeUpdate();
                pstmt.close();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }

        }

    /*************************************************************************************************************************************************************************************************************************************************************************/





/****************************************************************************************************************************************************************************************************************************************************************************/
    /************************************************************ 3. Download Dispatched Shipments From HUB To Operations **************************************************************************************************************************************/
    /************************************************************3.1 Download Dispached Shipment By Id (Run Time When Submit New Dispatch Shipment From HUB ) *************************************************************************************************************/
     public boolean DownloadDispatchShipmentById(int shipment_id) {
        String hub_query="";
        String op_query="";
        PreparedStatement op_stmt=null;
        ResultSet op_rs=null;
        PreparedStatement hub_stmt=null;
        ResultSet hub_rs=null;
        
        Connection hub_conn=createHUBConnection();
        Connection op_conn=createOperationConnection();
         hub_query="select id as shipment_id , shipment_expected_delivery_date as shipment_date  , cover_date , shippment_souce_id as facility_id , shippment_destination_id as driver_id , shipment_type_id as shipment_type \n" + 
         "                    , concat(users.display_name,' , ',log.logged_user) created_by\n" + 
         "                    from shippments \n" + 
         "                    , shipments_logs log  \n" + 
         "					, sc_users users  \n" + 
         "                    where  log.shipment_id=id  and \n" + 
         "					users.user_name=log.logged_user and id="+shipment_id;
            try {
                hub_stmt = hub_conn.prepareStatement(hub_query);
                hub_rs=hub_stmt.executeQuery();
                while(hub_rs.next()) {
                    op_query+="insert into shipment(shipment_id , shipment_date , cover_date , shipment_type, facility_id , driver_id, created_by ) values( \n"+
                        ""+hub_rs.getObject(hub_rs.findColumn("shipment_id"))+" , \n "+
                        "'"+hub_rs.getString(hub_rs.findColumn("shipment_date"))+" ' , \n "+
                        "'"+hub_rs.getObject(hub_rs.findColumn("cover_date"))+"' , \n"+
                        ""+hub_rs.getObject(hub_rs.findColumn("shipment_type"))+" , \n "+
                        ""+hub_rs.getObject("facility_id")+" , \n"+
                    "'"+hub_rs.getObject("driver_id")+"' , \n"+

                        "'"+hub_rs.getString(hub_rs.findColumn("created_by"))+"' ) ;";
                    
                }
                hub_query="select rel.product_id, rel.tretment_id as treatment_id , pr.product_sku , rel.planned_quantity , rel.actual_quantity\n" + 
                "from released_products_to_download rel\n" + 
                "left join products pr on pr.dms_product_id=rel.product_id and pr.treatment_id=rel.tretment_id and pr.type=1\n" + 
                "where rel.shipment_id="+shipment_id;
                hub_stmt=hub_conn.prepareStatement(hub_query);
                hub_rs=hub_stmt.executeQuery();
                while(hub_rs.next()) {
                    op_query+="insert into released_products (shipment_id , product_id , treatment_id , product_sku , planned_quantity , actual_quantity ) values( \n" +
                        ""+shipment_id+" , \n"+
                        "'"+hub_rs.getObject(hub_rs.findColumn("product_id"))+"' ,  \n"+
                    ""+hub_rs.getObject(hub_rs.findColumn("treatment_id"))+" , \n"+
                        "'"+hub_rs.getObject(hub_rs.findColumn("product_sku"))+"' , \n"+
                    ""+hub_rs.getObject(hub_rs.findColumn("planned_quantity"))+" , \n"+
                    ""+hub_rs.getObject(hub_rs.findColumn("actual_quantity"))+" );";
                }
                op_stmt=op_conn.prepareStatement(op_query);
                op_stmt.executeQuery();
                op_stmt.close();
                hub_stmt.close();
                op_conn.close();
                hub_conn.close();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return true;
        }

    /*********************************************************** 3.2 Download Missed Shipment That Not download at run time ******************************************************************************************************************************************************/
    public boolean DownloadMissedDispatchedShipments(String shipment_date) {
        Connection op_conn=createOperationConnection();
        Connection hub_conn=createHUBConnection();
        String op_query="";
        String hub_query="";
        String exist_shipments="";
        String not_exist_shipments="";
        PreparedStatement hub_stmt=null;
        PreparedStatement op_stmt=null;
        ResultSet hub_rs=null;
        ResultSet op_rs=null;
        op_query="select distinct(shipment_id) from shipment where shipment_type=1 ";
            try {
                op_stmt = op_conn.prepareStatement(op_query);
                op_rs=op_stmt.executeQuery();
                while(op_rs.next()) {
                    exist_shipments+=op_rs.getInt(op_rs.findColumn("shipment_id"))+",";
                    System.out.println(exist_shipments);
                }
                if(exist_shipments.length()>0) {
                    exist_shipments=exist_shipments.substring(0,exist_shipments.length()-1);
                    hub_query="select id as shipment_id , shipment_expected_delivery_date::date as shipment_date  , cover_date , shippment_souce_id as facility_id , shippment_destination_id as driver_id , shipment_type_id as shipment_type \n" + 
                    "                    , concat(users.display_name,' , ',log.logged_user) created_by\n" + 
                    "                    from shippments \n" + 
                    "                    , shipments_logs log  \n" + 
                    "					, sc_users users  \n" + 
                    "                    where shipment_type_id=1 and shipment_expected_delivery_date::date='"+shipment_date+"' and log.shipment_id=id  and \n" + 
                    "					users.user_name=log.logged_user and id not in ("+exist_shipments+")";
                System.out.println(hub_query);
                }
                else {
                    hub_query="select id as shipment_id , shipment_expected_delivery_date::date as shipment_date  , cover_date , shippment_souce_id as facility_id , shippment_destination_id as driver_id , shipment_type_id as shipment_type \n" + 
                    "                    , concat(users.display_name,' , ',log.logged_user) created_by\n" + 
                    "                    from shippments \n" + 
                    "                    , shipments_logs log  \n" + 
                    "					, sc_users users  \n" + 
                    "                    where shipment_type_id=1 and shipment_expected_delivery_date::date='"+shipment_date+"' and log.shipment_id=id  and \n" + 
                    "					users.user_name=log.logged_user";

                }
                hub_stmt=hub_conn.prepareStatement(hub_query);
                hub_rs=hub_stmt.executeQuery();
                op_query="insert into shipment(shipment_id , shipment_date , cover_date , shipment_type, facility_id , driver_id , created_by ) values(?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?)";
               op_stmt=op_conn.prepareStatement(op_query);
                while(hub_rs.next())
                    {
                    not_exist_shipments+=hub_rs.getInt(hub_rs.findColumn("shipment_id"))+",";
                    op_stmt.setObject(1, hub_rs.getObject(hub_rs.findColumn("shipment_id")));
                    op_stmt.setObject(2, hub_rs.getString(hub_rs.findColumn("shipment_date")));
                    op_stmt.setObject(3, hub_rs.getObject(hub_rs.findColumn("cover_date")));
                    op_stmt.setObject(4, hub_rs.getObject(hub_rs.findColumn("shipment_type")));
                    op_stmt.setObject(5, hub_rs.getObject("facility_id"));
                    op_stmt.setObject(6, hub_rs.getString(hub_rs.findColumn("driver_id")));
                    op_stmt.setObject(7, hub_rs.getString(hub_rs.findColumn("created_by")));
                    op_stmt.addBatch();
                      /*  op_query+="insert into shipment(shipment_id , shipment_date , cover_date , shipment_type, facility_id , driver_id , created_by ) values( \n"+
                            ""+hub_rs.getObject(hub_rs.findColumn("shipment_id"))+" , \n "+
                            "to_char(to_date('"+hub_rs.getString(hub_rs.findColumn("shipment_date"))+"','yyyy-mm-dd')) , \n "+
                            "to_char(to_date('"+hub_rs.getObject(hub_rs.findColumn("cover_date"))+"','yyyy-mm-dd' )) , \n"+
                            ""+hub_rs.getObject(hub_rs.findColumn("shipment_type"))+" ,  \n "+
                            ""+hub_rs.getObject("facility_id")+" , \n"+
                        "'"+hub_rs.getString(hub_rs.findColumn("driver_id"))+"' , \n"+

                            "'"+hub_rs.getString(hub_rs.findColumn("created_by"))+"' ) ;";
                        */
                    }
                op_stmt.executeBatch();
                op_conn.commit();
                System.out.println(op_query);
                if(not_exist_shipments.length()>0) {
                    not_exist_shipments=not_exist_shipments.substring(0, not_exist_shipments.length()-1);
                    hub_query="select rel.shipment_id , rel.product_id, rel.tretment_id as treatment_id , pr.product_sku , rel.planned_quantity , rel.actual_quantity\n" + 
                    "from released_products_to_download rel\n" + 
                    "left join products pr on pr.dms_product_id=rel.product_id and pr.treatment_id=rel.tretment_id and pr.type=1\n" + 
                    "where rel.shipment_id in ("+not_exist_shipments+");";
                    hub_stmt=hub_conn.prepareStatement(hub_query);
                    hub_rs=hub_stmt.executeQuery();
                    op_query="";
                    op_query+="insert into released_products (shipment_id , product_id , treatment_id , product_sku , planned_quantity , actual_quantity ) values(?,?,?,?,?,?)";
                    op_stmt=op_conn.prepareStatement(op_query);

                    while(hub_rs.next()) {
                        op_stmt.setObject(1, hub_rs.getObject(hub_rs.findColumn("shipment_id")));
                        op_stmt.setObject(2, hub_rs.getObject(hub_rs.findColumn("product_id")));
                        op_stmt.setObject(3, hub_rs.getObject(hub_rs.findColumn("treatment_id")));
                        op_stmt.setObject(4, hub_rs.getObject(hub_rs.findColumn("product_sku")));
                        op_stmt.setObject(5, hub_rs.getObject(hub_rs.findColumn("planned_quantity")));
                        op_stmt.setObject(6, hub_rs.getObject(hub_rs.findColumn("actual_quantity")));
                     /*   op_query+="insert into released_products (shipment_id , product_id , treatment_id , product_sku , planned_quantity , actual_quantity ) values( \n" +
                            ""+hub_rs.getObject(hub_rs.findColumn("shipment_id"))+" , \n"+
                            "'"+hub_rs.getObject(hub_rs.findColumn("product_id"))+"' ,  \n"+
                        ""+hub_rs.getObject(hub_rs.findColumn("treatment_id"))+" , \n"+
                            "'"+hub_rs.getObject(hub_rs.findColumn("product_sku"))+"' , \n"+
                        ""+hub_rs.getObject(hub_rs.findColumn("planned_quantity"))+" , \n"+
                        ""+hub_rs.getObject(hub_rs.findColumn("actual_quantity"))+" );";*/
                        op_stmt.addBatch();
                    }
                   // op_stmt=op_conn.prepareStatement(op_query);
                    op_stmt.executeBatch();
                    if(op_stmt!=null)
                    op_stmt.close();
                    if(op_conn!=null)
                    op_conn.close();
                    if(hub_stmt!=null)
                    hub_stmt.close();
                    if(hub_conn!=null)
                    hub_conn.close();
                    return true;
                }
                else {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return true;
        }

    /****************************************************************************************************************************************************************************************************************************************************************************************/


    
    /************************************************************************************************************************************************************************************************************************************************************************************/
    /***************************************************************4.1 Download DMS Tablet Actions To Operation ****************************************************************************************************************************************************************/
    public boolean DownloadAssignment_Actions() {
        Connection DMS_Conn=createDMSConnection();
        Connection OP_Conn=createOperationConnection();
        boolean product_ass=Update_DMS_Product_Assignment(DMS_Conn , OP_Conn);
        if(product_ass) {
           /* boolean coll_ass=Update_Collection_Assignment(DMS_Conn , OP_Conn);
            if(coll_ass) {
return true;
}*/
        }
        else {
            return false;
        }
        return true;
    }
    /************************************************************* 4.2 Download Products Assignments ***********************************************************************************************************************************************************************/
    public boolean Update_DMS_Product_Assignment(Connection DMS_COnn , Connection OP_Conn) {
        String DMS_Query="";
        String dms_update_query="";
        PreparedStatement DMS_Stmt=null;
        ResultSet DMS_rs=null;
        JSONArray assignments=new JSONArray();
         DMS_Query="SELECT ass.\"AID\" , ass.\"PRIORITY\" ,ass.\"RECIEPT_NO\",ass.\"STATUS\" , ass.\"CLOSE_CODE\" , ass.\"CLOSE_CODE_REASON\" , MAX(ass_d.\"ID\") action_id , ass_d.\"ACTION_DATE\" , ass_d.\"TOTAL_PRICE_PAID\" \n" + 
        "FROM \"ASSIGNMENTS_WORK_XY\" ass , \"ASSIGNED_CONTRACT_D\" ass_d WHERE \n" + 
        "ass.\"UPDATED\"=1 and ass_d.\"AID\"=ass.\"AID\" and \"ASSIGNMENT_TYPE\"=1 \n" + 
        "GROUP BY ass.\"AID\" , ass.\"PRIORITY\" ,ass.\"RECIEPT_NO\",ass.\"STATUS\" , ass.\"CLOSE_CODE\" , ass.\"CLOSE_CODE_REASON\" , ass_d.\"ACTION_DATE\" , ass_d.\"TOTAL_PRICE_PAID\"";
        try {
            DMS_Stmt = DMS_COnn.prepareStatement(DMS_Query);
            DMS_rs=DMS_Stmt.executeQuery();
            String action_ids="";
            String assignment_ids="";
            String Operation_ids="";
            while(DMS_rs.next()) {
                JSONObject ass=new JSONObject();
                try {
                    assignment_ids+=DMS_rs.getString(DMS_rs.findColumn("AID"))+",";
                 action_ids+=DMS_rs.getString(DMS_rs.findColumn("action_id"))+",";
                //    Operation_ids+=DMS_rs.getString(DMS_rs.findColumn("arg0"));
                    ass.put("aid", DMS_rs.getObject(DMS_rs.findColumn("AID")));
                    ass.put("priority",DMS_rs.getObject(DMS_rs.findColumn("PRIORITY")));
                    ass.put("assignment_id" , DMS_rs.getObject(DMS_rs.findColumn("RECIEPT_NO")));
                    ass.put("status" , DMS_rs.getObject(DMS_rs.findColumn("STATUS")));
                    ass.put("close_code" , DMS_rs.getObject(DMS_rs.findColumn("CLOSE_CODE")));
                    ass.put("close_reason" , DMS_rs.getObject(DMS_rs.findColumn("CLOSE_CODE_REASON")));
                    ass.put("action_id", DMS_rs.getObject(DMS_rs.findColumn("action_id")));
                    ass.put("action_date", DMS_rs.getObject(DMS_rs.findColumn("ACTION_DATE")));
                    ass.put("total_price_paid", DMS_rs.getObject(DMS_rs.findColumn("TOTAL_PRICE_PAID")));
                    JSONArray products=GetDMSAssignmentProducts(DMS_rs.getInt(DMS_rs.findColumn("aid")), DMS_COnn);
                    ass.put("products",products);
                    assignments.put(ass);
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }
            }
           CallableStatement statement = OP_Conn.prepareCall(" { call load_dms_product_assignment_updates( ?::json) } ");
            statement.setObject(1, assignments.toString());
            statement.execute();
            statement.close();
            if(action_ids.length()>1) {
                assignment_ids=assignment_ids.substring(0, assignment_ids.length()-1);
                action_ids=action_ids.substring(0,action_ids.length()-1);
                boolean mark_action_downloaded=MarkAssignmentActionAsDownloaded( assignment_ids ,  action_ids , DMS_COnn,1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public JSONArray GetDMSAssignmentProducts(int assignment_id , Connection DMS_Conn) {
        JSONArray products =new JSONArray();
        PreparedStatement DMS_Stmt=null;
        ResultSet DMS_rs=null;
        String DMS_Query="select \"ITEM_ID\" as package_id , \"COMPONENT_ID\" as product_id , \"QUNTITY\" as quantity , \"QUNTITY_REPLACED\" as quantity_replaced , \n" + 
        "\"UNIT_PRICE\" as price , \"BUY_PRICE\" as buy_price , \"Treatment code\" as treatment_id , \"Package_NO\" as pacakage_no \n" + 
        ",\"Quantity_cancel\" as cancel_quantity , \"Quantity_Dirty\" as dirty_quantity , \"HANDLE\" as handle  \n" + 
        "from \"PRODUCT_CONTRACT\" where \"AID\"="+assignment_id;
        try {
            DMS_Stmt = DMS_Conn.prepareStatement(DMS_Query);
            DMS_rs=DMS_Stmt.executeQuery();
            while(DMS_rs.next()) {
                JSONObject obj=new JSONObject();
                try {
                    obj.put("package_id", DMS_rs.getObject(DMS_rs.findColumn("package_id")));
                    obj.put("product_id", DMS_rs.getObject(DMS_rs.findColumn("product_id")));
                    obj.put("quantity", DMS_rs.getObject(DMS_rs.findColumn("quantity")));
                    obj.put("quantity_replaced", DMS_rs.getObject(DMS_rs.findColumn("quantity_replaced")));
                    obj.put("price", DMS_rs.getObject(DMS_rs.findColumn("price")));
                    obj.put("buy_price", DMS_rs.getObject(DMS_rs.findColumn("buy_price")));
                    obj.put("treatment_id", DMS_rs.getObject(DMS_rs.findColumn("treatment_id")));
                    obj.put("pacakage_no", DMS_rs.getObject(DMS_rs.findColumn("pacakage_no")));
                    obj.put("cancel_quantity", DMS_rs.getObject(DMS_rs.findColumn("cancel_quantity")));
                    obj.put("dirty_quantity", DMS_rs.getObject(DMS_rs.findColumn("dirty_quantity")));
                    obj.put("handle", DMS_rs.getObject(DMS_rs.findColumn("handle")));
products.put(obj);
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
    /************************************************************** 4.3 Download Collection Assignments ********************************************************************************************************************************************************************************/
    public boolean Update_Collection_Assignment(Connection DMS_COnn , Connection OP_Conn) {
        String DMS_Query="";
        String assignment_ids="";
        String action_ids="";
        PreparedStatement DMS_Stmt=null;
        ResultSet DMS_rs=null;
        JSONArray assignments=new JSONArray();
         DMS_Query="SELECT ass.\"AID\" , ass.\"PRIORITY\" ,ass.\"RECIEPT_NO\",ass.\"STATUS\" , ass.\"CLOSE_CODE\" , ass.\"CLOSE_CODE_REASON\" , MAX(ass_d.\"ID\") action_id , ass_d.\"ACTION_DATE\" , ass_d.\"TOTAL_PRICE_PAID\" \n" + 
        "FROM \"ASSIGNMENTS_WORK_XY\" ass , \"ASSIGNED_CONTRACT_D\" ass_d WHERE \n" + 
        "ass.\"UPDATED\"=1 and ass_d.\"AID\"=ass.\"AID\" and \"ASSIGNMENT_TYPE\"=4 \n" + 
        "GROUP BY ass.\"AID\" , ass.\"PRIORITY\" ,ass.\"RECIEPT_NO\",ass.\"STATUS\" , ass.\"CLOSE_CODE\" , ass.\"CLOSE_CODE_REASON\" , ass_d.\"ACTION_DATE\" , ass_d.\"TOTAL_PRICE_PAID\"";
        try {
            DMS_Stmt = DMS_COnn.prepareStatement(DMS_Query);
            DMS_rs=DMS_Stmt.executeQuery();
            while(DMS_rs.next()) {
                assignment_ids+=DMS_rs.getString(DMS_rs.findColumn("AID"))+",";
                action_ids+=DMS_rs.getString(DMS_rs.findColumn("action_id"))+",";
                JSONObject ass=new JSONObject();
                try {
                    ass.put("aid", DMS_rs.getObject(DMS_rs.findColumn("AID")));
                    ass.put("priority",DMS_rs.getObject(DMS_rs.findColumn("PRIORITY")));
                    ass.put("assignment_id" , DMS_rs.getObject(DMS_rs.findColumn("RECIEPT_NO")));
                    ass.put("status" , DMS_rs.getObject(DMS_rs.findColumn("STATUS")));
                    ass.put("close_code" , DMS_rs.getObject(DMS_rs.findColumn("CLOSE_CODE")));
                    ass.put("close_reason" , DMS_rs.getObject(DMS_rs.findColumn("CLOSE_CODE_REASON")));
                    ass.put("action_id", DMS_rs.getObject(DMS_rs.findColumn("action_id")));
                    ass.put("action_date", DMS_rs.getObject(DMS_rs.findColumn("ACTION_DATE")));
                    ass.put("total_price_paid", DMS_rs.getObject(DMS_rs.findColumn("TOTAL_PRICE_PAID")));
                    JSONArray collections=GetDMSAssignmentCollections(DMS_rs.getInt(DMS_rs.findColumn("aid")), DMS_COnn);
                    ass.put("collections",collections);
                    assignments.put(ass);
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }
            }
            CallableStatement statement = OP_Conn.prepareCall(" { call load_dms_collection_assignment_updates( ?::json) } ");
             statement.setObject(1, assignments.toString());
             statement.execute();
             statement.close();
            if(action_ids.length()>1) {
                assignment_ids=assignment_ids.substring(0, assignment_ids.length()-1);
                action_ids=action_ids.substring(0,action_ids.length()-1);
                boolean mark_action_downloaded=MarkAssignmentActionAsDownloaded( assignment_ids ,  action_ids , DMS_COnn,4);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public JSONArray GetDMSAssignmentCollections(int assignment_id , Connection DMS_COnn) {
        JSONArray collections =new JSONArray();
        String DMS_Query="";
        PreparedStatement DMS_Stmt=null;
        ResultSet DMS_rs=null;
        DMS_Query="select \"INVOICE_NO\" invoice_no , \"AMOUNT\" amount , \"PAID_AMOUNT\" paid_amount , \"DATE\" invoice_date\n" + 
        "from \"COLLECTION_CONTRACT\"\n" + 
        "where \"AID\"="+assignment_id;
        try {
            DMS_Stmt = DMS_COnn.prepareStatement(DMS_Query);
            DMS_rs=DMS_Stmt.executeQuery();
            while(DMS_rs.next()) {
                JSONObject coll=new JSONObject();
                try {
                    coll.put("invoice_no", DMS_rs.getObject(DMS_rs.findColumn("invoice_no")));
                    coll.put("amount", DMS_rs.getObject(DMS_rs.findColumn("amount")));
                    coll.put("paid_amount", DMS_rs.getObject(DMS_rs.findColumn("paid_amount")));
                    coll.put("invoice_date", DMS_rs.getObject(DMS_rs.findColumn("invoice_date")));
                    collections.put(coll);

                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return collections;
    }
    /*************************************************************** 4.4 Mark Assignmnets On DMS As Updated ***************************************************************************************************************************************************************************/    
   public boolean MarkAssignmentActionAsDownloaded(String assignment_ids , String action_ids , Connection DMS_COnn,int ass_type) {
       String DMS_Query="";
       PreparedStatement stmt=null;
       DMS_Query="UPDATE \"ASSIGNMENTS_WORK_XY\" SET \"UPDATED\"=0 , \"LAST_DOWNLOAD\"= CURRENT_TIMESTAMP WHERE \"AID\" IN ("+assignment_ids+") ; ";
       DMS_Query+="UPDATE \"ASSIGNED_CONTRACT_D\" SET \"Oracle_flag\"=1 WHERE \"ID\" IN ("+action_ids+") ;";
       if(ass_type==4) {
           DMS_Query+="UPDATE \"COLLECTION_CONTRACT\" SET \"ORACLE_FLAG\"=1 WHERE \"AID\" IN ("+assignment_ids+"); ";
       }
        try {
            stmt = DMS_COnn.prepareStatement(DMS_Query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
   }
    /**********************************************************************************************************************************************************************************************************************************************************************************/



    /************************************************************* 5. Download Reconciliations Back To Operation ************************************************************************************************************************************************************************************/
    /**************************************************************5.1 Product Reconciliation Between Driver & HUB *****************************************************************************************************************************************************************/
    public boolean DownloadDriverHUBReconciliations(String return_date) {
        Connection op_conn=createOperationConnection();
        Connection hub_conn=createHUBConnection();
        PreparedStatement hub_stmt=null;
        PreparedStatement op_stmt=null;
        PreparedStatement op_stmt_1=null;
        ResultSet hub_rs=null;
        ResultSet op_rs=null;
        String hub_query="";
        String op_query="";
        String shipment_ids="";
        String op_query2="";
        hub_query="select id as shipment_id , shipment_expected_delivery_date::date as shipment_date, sh.related_shipments , shipment_type_id as shipment_type , shippment_souce_id as driver_id , \n" + 
        "        shippment_destination_id as facility_id ,concat(u.display_name,' , ',shl.logged_user) as created_by\n" + 
        "        from shippments sh  \n" + 
        "        left join shipments_logs shl on shl.shipment_id=sh.id and (log_id = (select min(log_id) from shipments_logs where shipment_id=sh.id))\n" + 
        "               left join sc_users u on u.user_name=shl.logged_user                                                                                                            \n" + 
        "        where sh.shipment_expected_delivery_date::date = '"+return_date+"' and sh.shipment_type_id=2";
            try {
                hub_stmt = hub_conn.prepareStatement(hub_query);
                hub_rs=hub_stmt.executeQuery();
                op_query+="insert into shipment (shipment_id , shipment_date , shipment_type , driver_id , facility_id , created_by , related_shipments ) values(?,?,?,?,?,?,?)";
                op_stmt_1=op_conn.prepareStatement(op_query);
                while(hub_rs.next()) {
                    System.err.println(hub_rs.getObject(hub_rs.findColumn("shipment_date")));
                    shipment_ids+=hub_rs.getInt(hub_rs.findColumn("shipment_id"))+",";
                    op_stmt_1.setObject(1, hub_rs.getInt(hub_rs.findColumn("shipment_id")));
                    op_stmt_1.setObject(2, hub_rs.getObject(hub_rs.findColumn("shipment_date")));
                    op_stmt_1.setObject(3, hub_rs.getObject(hub_rs.findColumn("shipment_type")));
                    op_stmt_1.setObject(4, hub_rs.getObject(hub_rs.findColumn("driver_id")));
                    op_stmt_1.setObject(5, hub_rs.getObject(hub_rs.findColumn("facility_id")));
                    op_stmt_1.setObject(6, hub_rs.getObject(hub_rs.findColumn("created_by")));
                    op_stmt_1.setObject(7, hub_rs.getObject(hub_rs.findColumn("related_shipments")));
op_stmt_1.addBatch();
                    /*  op_query+= ""+hub_rs.getInt(hub_rs.findColumn("shipment_id"))+" , ";
                      op_query+=  "to_char(to_date('"+hub_rs.getObject(hub_rs.findColumn("shipment_date"))+"','yyyy-mm-dd')) ,";
                    op_query+=""+hub_rs.getObject(hub_rs.findColumn("shipment_type"))+" ,";
                   op_query+= "'"+hub_rs.getObject(hub_rs.findColumn("driver_id"))+"' ,";
                   op_query+= ""+hub_rs.getObject(hub_rs.findColumn("facility_id"))+" ,";
                  op_query+=  "'"+hub_rs.getObject(hub_rs.findColumn("created_by"))+"' , ";
                  op_query+=  "'"+hub_rs.getObject(hub_rs.findColumn("related_shipments"))+"' );";
System.out.println(op_query);*/
                }
                if(shipment_ids.length()>0)
                {
                    System.out.println(shipment_ids);
                    shipment_ids=shipment_ids.substring(0, shipment_ids.length()-1);
                 hub_query="select recon.clean_shipped as out_qty , car.shipment_id,car.dms_product_id product_id , car.actual_clean clean_qty , car.clean_dms clean_expected_recon_qty ,\n" + 
                 "car.actual_dirty dirty_qty , car.dirty_dms dirty_expected_reconciliation_qty , \n" + 
                 "car.clean_manual_new manual_new_qty , car.clean_expected_new , car.clean_actual_new ,recon.dirty_actual_cancel, recon.dirty_cancel cancel_qty , \n" + 
                 "recon.dirty_manual_cancel manual_cancel_qty , recon.dirty_lost lost , recon.dirty_rlost rlost , recon.clean_actual_calculated_qty , \n" + 
                 "recon.dirty_actual_calculated_qty\n" + 
                 "from car_validation_data car \n" + 
                 "left join reconciliated_products_to_download recon on recon.shipment_id =car.shipment_id and recon.dms_product_id=car.dms_product_id\n" + 
                 "where car.shipment_id in ("+shipment_ids+")"  ;
                 hub_stmt=hub_conn.prepareStatement(hub_query);
                 hub_rs=hub_stmt.executeQuery();
                    op_query2+="insert into reconciliated_shipment_product(shipment_id,product_id,out_quantity,clean_quantity,dirty_quantity,manual_new_quantity,clean_expected_new,clean_actual_new,cancel_quantity,manual_cancel_quantity,lost,rlost,clean_expected_recon_qty,clean_actual_calculated_qty,DIRTY_EXPECTED_RECONCILIATION,dirty_actual_calculated_qty,dirty_actual_cancel) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
op_stmt=op_conn.prepareStatement(op_query2);
                 while(hub_rs.next()) {
                    // op_query+="insert into reconciliated_shipment_product(shipment_id,product_id,out_quantity,clean_quantity,dirty_quantity,manual_new_quantity,clean_expected_new,clean_actual_new,cancel_quantity,manual_cancel_quantity,lost,rlost,clean_expected_recon_qty,clean_actual_calculated_qty,dirty_expected_reconciliation_qty,dirty_actual_calculated_qty,dirty_actual_cancel) values (";
                     op_stmt.setObject(1, hub_rs.getInt(hub_rs.findColumn("shipment_id")));
                     op_stmt.setObject(2, hub_rs.getObject(hub_rs.findColumn("product_id")));
                     op_stmt.setObject(3, hub_rs.getObject(hub_rs.findColumn("out_qty")));
                     op_stmt.setObject(4, hub_rs.getObject(hub_rs.findColumn("clean_qty")));
                     op_stmt.setObject(5, hub_rs.getObject(hub_rs.findColumn("dirty_qty")));
                     op_stmt.setObject(6, hub_rs.getObject(hub_rs.findColumn("manual_new_qty")));
                     op_stmt.setObject(7, hub_rs.getObject(hub_rs.findColumn("clean_expected_new")));
                     op_stmt.setObject(8, hub_rs.getObject(hub_rs.findColumn("clean_actual_new")));
                     op_stmt.setObject(9, hub_rs.getObject(hub_rs.findColumn("cancel_qty")));
                     op_stmt.setObject(10, hub_rs.getObject(hub_rs.findColumn("manual_cancel_qty")));
                     op_stmt.setObject(11, hub_rs.getObject(hub_rs.findColumn("lost")));
                     op_stmt.setObject(12, hub_rs.getObject(hub_rs.findColumn("rlost")));
                     op_stmt.setObject(13, hub_rs.getObject(hub_rs.findColumn("clean_expected_recon_qty")));
                     op_stmt.setObject(14, hub_rs.getObject(hub_rs.findColumn("clean_actual_calculated_qty")));
                     op_stmt.setObject(15, hub_rs.getObject(hub_rs.findColumn("dirty_expected_reconciliation_qty")));
                     op_stmt.setObject(16, hub_rs.getObject(hub_rs.findColumn("dirty_actual_calculated_qty")));
                     op_stmt.setObject(17, hub_rs.getObject(hub_rs.findColumn("dirty_actual_cancel")));
                     op_stmt.addBatch();
                    /* op_query+=""+hub_rs.getInt(hub_rs.findColumn("shipment_id"))+",";
                     op_query+="'"+hub_rs.getObject(hub_rs.findColumn("product_id"))+"' , ";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("out_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("clean_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("dirty_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("manual_new_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("clean_expected_new"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("clean_actual_new"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("cancel_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("manual_cancel_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("lost"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("rlost"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("clean_expected_recon_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("clean_actual_calculated_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("dirty_expected_reconciliation_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("dirty_actual_calculated_qty"))+",";
                     op_query+=""+hub_rs.getObject(hub_rs.findColumn("dirty_actual_cancel"))+");"; */
                 }
                 System.out.println(op_query);
                  //  op_stmt=op_conn.prepareStatement(op_query);
                 op_stmt_1.executeBatch();
                 op_stmt.executeBatch();
                   // op_stmt.executeQuery();
                   op_conn.commit();
                   if(op_stmt_1!=null)
                       op_stmt_1.close();
                   if(op_stmt!=null)
                    op_stmt.close();
                   if(hub_stmt!=null)
                    hub_stmt.close();
                   if(op_conn!=null)
                    op_conn.close();
                   if(hub_conn!=null)
                    hub_conn.close();
                    return true;
                }
                else {
                    return true;
                }
                     
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return true;
        }
    /************************************************************** 5.2 Cash Reconciliation Between Driver & HUB ****************************************************************************************************************************************************************************/
    public boolean DownloadDriverHUBCashAmount(String return_date) {
        String hub_query="";
        String op_query="";
        PreparedStatement hub_stmt=null;
        ResultSet hub_rs=null;
        Connection Hub_conn=createHUBConnection();
        Connection OP_Conn=createOperationConnection();
        PreparedStatement op_stmt=null;
        String shipment_ids="";
        hub_query="SELECT shipment_id,shipment_date,driver_id,expected_money,actual_money,reconciled_money,agent_name from reconcilied_cash_to_download where shipment_date='"+return_date+"'";
            try {
                hub_stmt = Hub_conn.prepareStatement(hub_query);
                hub_rs=hub_stmt.executeQuery();
                op_query+="insert into hub_reconciliation_amount(shipment_id , transaction_date , expected_amount , reconciled_amount , actual_amount , created_by ) values(?,?,?,?,?,?)";
                op_stmt=OP_Conn.prepareStatement(op_query);

                while(hub_rs.next()) {
                    shipment_ids+=hub_rs.getInt(hub_rs.findColumn("shipment_id"))+',';
                    op_stmt.setObject(1, hub_rs.getObject(hub_rs.findColumn("shipment_id")));
                    op_stmt.setObject(2, hub_rs.getObject(hub_rs.findColumn("shipment_date")));
                    op_stmt.setObject(3, hub_rs.getObject(hub_rs.findColumn("expected_money")));
                    op_stmt.setObject(4, hub_rs.getObject(hub_rs.findColumn("reconciled_money")));
                    op_stmt.setObject(5, hub_rs.getObject(hub_rs.findColumn("actual_money")));
                    op_stmt.setObject(6, hub_rs.getObject(hub_rs.findColumn("agent_name")));
op_stmt.addBatch();
                 /*  op_query+=""+hub_rs.getObject(hub_rs.findColumn("shipment_id"))+",";
                    op_query+="'"+hub_rs.getObject(hub_rs.findColumn("shipment_date"))+"' , ";
                    op_query+=""+hub_rs.getObject(hub_rs.findColumn("expected_money"))+",";
                    op_query+=""+hub_rs.getObject(hub_rs.findColumn("reconciled_money"))+",";
                    op_query+=""+hub_rs.getObject(hub_rs.findColumn("actual_money"))+",";
                    op_query+="'"+hub_rs.getObject(hub_rs.findColumn("agent_name"))+"');";*/

                }
                System.out.println(shipment_ids);

                if(shipment_ids.length()>0) {
                    op_stmt.executeBatch();
                    OP_Conn.commit();
                }
               
               if(hub_rs!=null)
                hub_rs.close();
                if(hub_stmt!=null)
                hub_stmt.close();
                if(Hub_conn!=null)
                Hub_conn.close();
                if(op_stmt!=null)
                op_stmt.close();
                if(OP_Conn!=null)
                OP_Conn.close();
 
            } 
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return true;
        }
   /*************************************************************** 5.3 Cash Reconciliation Between Driver & Pilot ****************************************************************************************************************************************************************************/
public boolean DownloadDriverPilotAmounts(String return_date) {
    Connection DMS_conn=createHUBConnection();
    Connection OP_conn=createOperationConnection();
    PreparedStatement DMS_stmt=null;
    PreparedStatement OP_stmt=null;
    ResultSet DMS_rs=null;
    String DMS_query="";
    String MID="";
    String OP_query="";
    if(DMS_conn!=null && OP_conn!=null )
    {
    DMS_query="select \"ID\" , \"DRIVER\" , \"PILOT\" , \"AREA\" , \"DATE\" , \"EXPECTED_MONEY\" , \"ACTUAL_MONEY\" , \"APPROVED_MONEY\" , \"ACCEPT\" , \n" + 
    "        \"NOTES\" , concat(\"DATE\" , ' ' ,\"TIME\") :: timestamp as reconciliation_time , min(rel.master_shipment_id) shipment_id \n" + 
    "        from \"RECONCILIATION_MASTER\"  \n" + 
    "    	left join related_shipments_to_download rel on rel.driver=\"RECONCILIATION_MASTER\".\"DRIVER\" AND rel.cover_date=\"DATE\" \n" + 
    "     	where \"DATE\"='"+return_date+"' and \"ACCEPT\"=true\n" + 
    "    	GROUP BY \"ID\" , \"DRIVER\" , \"PILOT\" , \"AREA\" , \"DATE\" , \"EXPECTED_MONEY\" , \"ACTUAL_MONEY\" , \"APPROVED_MONEY\" , \"ACCEPT\" , \n" + 
    "        \"NOTES\" , concat(\"DATE\" , ' ' ,\"TIME\") :: timestamp";
        try {
            DMS_stmt = DMS_conn.prepareStatement(DMS_query);
            DMS_rs=DMS_stmt.executeQuery();
            OP_query+="insert into reconciliation_driver_pilot(id , driver_id,pilot_id , area_id,return_date,expected_money, actual_money , approved_money , accept , reconciliation_time,notes , shipment_id) values (?,?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?)" ;
OP_stmt=OP_conn.prepareStatement(OP_query);
            while(DMS_rs.next()) {
                OP_stmt.setObject(1, DMS_rs.getObject(DMS_rs.findColumn("ID")));
                OP_stmt.setObject(2, DMS_rs.getString(DMS_rs.findColumn("DRIVER")));
                OP_stmt.setObject(3, DMS_rs.getString(DMS_rs.findColumn("PILOT")));
                OP_stmt.setObject(4, DMS_rs.getObject(DMS_rs.findColumn("AREA")));
                System.err.println(DMS_rs.getString(DMS_rs.findColumn("DATE")));
                OP_stmt.setObject(5, DMS_rs.getString(DMS_rs.findColumn("DATE")));
                OP_stmt.setObject(6, DMS_rs.getObject(DMS_rs.findColumn("EXPECTED_MONEY")));
                OP_stmt.setObject(7, DMS_rs.getObject(DMS_rs.findColumn("ACTUAL_MONEY")));
                OP_stmt.setObject(8, DMS_rs.getObject(DMS_rs.findColumn("APPROVED_MONEY")));
                OP_stmt.setObject(9, DMS_rs.getObject(DMS_rs.findColumn("ACCEPT")));
                OP_stmt.setObject(10, DMS_rs.getObject(DMS_rs.findColumn("reconciliation_time")));
                OP_stmt.setObject(11, DMS_rs.getObject(DMS_rs.findColumn("NOTES")));
                OP_stmt.setObject(12, DMS_rs.getObject(DMS_rs.findColumn("shipment_id")));

OP_stmt.addBatch();
              /*  OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("ID"))+" , ";
                 OP_query+=  "'"+DMS_rs.getString(DMS_rs.findColumn("DRIVER"))+"' ,  ";  
                  OP_query+=   "'"+DMS_rs.getString(DMS_rs.findColumn("PILOT"))+"' , ";
                   OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("AREA"))+" , ";
                  OP_query+=   "'"+DMS_rs.getString(DMS_rs.findColumn("DATE"))+"' , ";
                  OP_query+=   ""+DMS_rs.getObject(DMS_rs.findColumn("EXPECTED_MONEY"))+" , ";
                   OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("ACTUAL_MONEY"))+" , ";
                   OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("APPROVED_MONEY"))+" , ";
                   OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("ACCEPT"))+" ,";
                  OP_query+=   "'"+DMS_rs.getObject(DMS_rs.findColumn("reconciliation_time"))+"' ,";
                OP_query+=   "'"+DMS_rs.getObject(DMS_rs.findColumn("NOTES"))+"' );";*/

            }
           // OP_stmt=OP_conn.prepareStatement(OP_query);
            OP_stmt.executeBatch();
            if(OP_stmt!=null)
            OP_stmt.close();
            if(DMS_rs!=null)
            DMS_rs.close();
            if(DMS_stmt!=null)
            DMS_stmt.close();
            if(DMS_conn!=null)
            DMS_conn.close();
            return true;
        }
                    catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    else {
        return false;
    }
    return  true;
    }
    /*************************************************************** 5.4 Product Reconciliation Between Driver & Pilot ***********************************************************************************************************************************************************************/
    public boolean DownloadDriverPilotProducts(String return_date) {
        Connection DMS_conn=createDMSConnection();
        Connection OP_conn=createOperationConnection();
        PreparedStatement DMS_stmt=null;
        PreparedStatement OP_stmt=null;
        ResultSet DMS_rs=null;
        String DMS_query="";
        String OP_query="";
        System.out.println(return_date);
        DMS_query="SELECT \"MID\" mid ,\"DATE\" AS return_date ,\"PILOT\" AS pilot_id , \"DRIVER\" AS driver_id , \"PRODUCT\" AS product_id , \n" + 
        "                    \"TREATMENT\" treatment_id , \"ACTUAL_CLEAN\" AS clean_quatity , \n" + 
        "                   \"ACTUAL_DIRTY\" AS dirty_quantity , (\"EXP_CLEAN\"-\"ACTUAL_CLEAN\")+(\"EXP_DIRTY\"-\"ACTUAL_DIRTY\") as lost_over ,  \n" + 
        "                   \"NEW_ACTUAL\" AS new , \"CANCEL_ACTUAL\" AS cancel,\"R_LOST\" AS rlost  , \"EXP_CLEAN\" , \"EXP_DIRTY\" , \"NEW_DMS\" , \"CANCEL_DMS\"\n" + 
        "                       FROM public.\"RECONCILATION_DETAIL\" ,\"RECONCILIATION_MASTER\",\"PRODUCT\" AS \"PRODUCTS\"  \n" + 
        "                       where \"MID\"=\"RECONCILIATION_MASTER\".\"ID\"\n" + 
        "                       and \"DATE\"='"+return_date+"' AND \"ACCEPT\"=TRUE \n" + 
        "                       AND \"PRODUCT_ID\"=\"PRODUCT\";";
            try {
                System.out.println(DMS_query);
                DMS_stmt = DMS_conn.prepareStatement(DMS_query);
                DMS_rs=DMS_stmt.executeQuery();
                OP_query+="insert into reconciliation_driver_pilot_d(reconciliation_id,product_id,treatment_id,clean_quantity,dirty_quantity,lost_over,new,cancel,r_lost,expected_clean , expected_dirty , expected_new , expected_cancel) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
OP_stmt=OP_conn.prepareStatement(OP_query);
                while(DMS_rs.next()) {
                    OP_stmt.setObject(1, DMS_rs.getObject(DMS_rs.findColumn("mid")));
                    OP_stmt.setObject(2, DMS_rs.getObject(DMS_rs.findColumn("product_id")));
                    OP_stmt.setObject(3, DMS_rs.getObject(DMS_rs.findColumn("treatment_id")));
                    OP_stmt.setObject(4, DMS_rs.getObject(DMS_rs.findColumn("clean_quatity")));
                    OP_stmt.setObject(5, DMS_rs.getObject(DMS_rs.findColumn("dirty_quantity")));
                    OP_stmt.setObject(6, DMS_rs.getObject(DMS_rs.findColumn("lost_over")));
                    OP_stmt.setObject(7, DMS_rs.getObject(DMS_rs.findColumn("new")));
                    OP_stmt.setObject(8, DMS_rs.getObject(DMS_rs.findColumn("cancel")));
                    OP_stmt.setObject(9, DMS_rs.getObject(DMS_rs.findColumn("rlost")));
                    OP_stmt.setObject(10, DMS_rs.getObject(DMS_rs.findColumn("EXP_CLEAN")));
                    OP_stmt.setObject(11, DMS_rs.getObject(DMS_rs.findColumn("EXP_DIRTY")));
                    OP_stmt.setObject(12, DMS_rs.getObject(DMS_rs.findColumn("NEW_DMS")));
                    OP_stmt.setObject(13, DMS_rs.getObject(DMS_rs.findColumn("CANCEL_DMS")));
OP_stmt.addBatch();



                  /* OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("mid"))+" , ";
                   OP_query+= "'"+DMS_rs.getObject(DMS_rs.findColumn("product_id"))+"' , ";
                   OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("treatment_id"))+" , ";
                   OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("clean_quatity"))+" , ";
                   OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("dirty_quantity"))+" , ";
                   OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("lost_over"))+" , ";
                   OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("new"))+" , ";
                   OP_query+= ""+DMS_rs.getObject(DMS_rs.findColumn("cancel"))+" , ";
                  OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("rlost"))+" ,";
                    OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("EXP_CLEAN"))+" ,";
                    OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("EXP_DIRTY"))+" ,";
                    OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("NEW_DMS"))+" ,";
                    OP_query+=  ""+DMS_rs.getObject(DMS_rs.findColumn("CANCEL_DMS"))+" );";
*/
                }
              //  OP_stmt=OP_conn.prepareStatement(OP_query);
                OP_stmt.executeBatch();
                OP_conn.commit();
                System.out.println(OP_query);
                if(DMS_rs!=null)
                DMS_rs.close();
                if(DMS_stmt!=null)
                DMS_stmt.close();
                if(DMS_conn!=null)
                DMS_conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                
            }
            return true;

        }
/********************************************************************* 5.5 Driver Cover Product ****************************************************************************************************************************************************************************************************************************/
public boolean GetDriverCoverOnDate(String Assign_date ) {
    Connection DMS_Conn=createDMSConnection();
    Connection OP_Conn=createOperationConnection();
    PreparedStatement DMS_stmt=null;
    PreparedStatement OP_stmt=null;
    ResultSet DMS_rs=null;
    String DMS_Query="";
    String OP_Query="";
    DMS_Query="select \"ID\" , \"PRODUCT_ID\" , \"Treatment_ID\" , \"QUANTITY\" , \"ASSIGNMENT_DATE\" , \"Area_ID\" , \"Cover_Sequence\" , \"Current_Quantity\",\n" + 
    "\"Dirty_Quantity\" , \"DRIVER_ID\" , \"RECONCILIATION_FLAG\" ,\"RELEASING_DATE\" , \"SHIPMENT_ID\" , \"MANUAL_NEW\" , \"MANUAL_CANCEL\"\n" + 
    ",\"LOST\" , \"R_LOST\" \n" + 
    "FROM \"COVER_PRODUCT\"\n" + 
    "where \"ASSIGNMENT_DATE\"::DATE='"+Assign_date+"' ";
        try {
            DMS_stmt = DMS_Conn.prepareStatement(DMS_Query);
            DMS_rs=DMS_stmt.executeQuery();
            OP_Query+="insert into cover_product(id,product_id,treatment_id,quantity,assignment_date,area_id,cover_id,clean_quantity,dirty_quantity,driver_id,reconciliation_flag,releasing_date,shipment_id,manual_new,manual_cancel,lost,rlost) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
OP_stmt=OP_Conn.prepareStatement(OP_Query);
            while(DMS_rs.next()) {
                OP_stmt.setObject(1, DMS_rs.getObject(DMS_rs.findColumn("ID")));
                OP_stmt.setObject(2, DMS_rs.getObject(DMS_rs.findColumn("PRODUCT_ID")));
                OP_stmt.setObject(3, DMS_rs.getObject(DMS_rs.findColumn("Treatment_ID")));
                OP_stmt.setObject(4, DMS_rs.getObject(DMS_rs.findColumn("QUANTITY")));
                OP_stmt.setObject(5, DMS_rs.getObject(DMS_rs.findColumn("ASSIGNMENT_DATE")));
                OP_stmt.setObject(6, DMS_rs.getObject(DMS_rs.findColumn("Area_ID")));
                OP_stmt.setObject(7, DMS_rs.getObject(DMS_rs.findColumn("Cover_Sequence")));
                OP_stmt.setObject(8, DMS_rs.getObject(DMS_rs.findColumn("Current_Quantity")));
                OP_stmt.setObject(9, DMS_rs.getObject(DMS_rs.findColumn("Dirty_Quantity")));
                OP_stmt.setObject(10, DMS_rs.getObject(DMS_rs.findColumn("DRIVER_ID")));
                OP_stmt.setObject(11, DMS_rs.getObject(DMS_rs.findColumn("RECONCILIATION_FLAG")));
                OP_stmt.setObject(12, DMS_rs.getObject(DMS_rs.findColumn("RELEASING_DATE")));
                OP_stmt.setObject(13, DMS_rs.getObject(DMS_rs.findColumn("SHIPMENT_ID")));
                OP_stmt.setObject(14, DMS_rs.getObject(DMS_rs.findColumn("MANUAL_NEW")));
                OP_stmt.setObject(15, DMS_rs.getObject(DMS_rs.findColumn("MANUAL_CANCEL")));
                OP_stmt.setObject(16, DMS_rs.getObject(DMS_rs.findColumn("LOST")));
                OP_stmt.setObject(17, DMS_rs.getObject(DMS_rs.findColumn("R_LOST")));
                OP_stmt.addBatch();

              /*  OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("ID"))+",";
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("PRODUCT_ID"))+"',";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Treatment_ID"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("QUANTITY"))+",";
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("ASSIGNMENT_DATE"))+"',";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Area_ID"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Cover_Sequence"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Current_Quantity"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Dirty_Quantity"))+",";
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("DRIVER_ID"))+"',";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("RECONCILIATION_FLAG"))+",";
                if(DMS_rs.getObject(DMS_rs.findColumn("RELEASING_DATE"))==null)
                    OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("RELEASING_DATE"))+",";
                else
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("RELEASING_DATE"))+"',";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("SHIPMENT_ID"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("MANUAL_NEW"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("MANUAL_CANCEL"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("LOST"))+",";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("R_LOST"))+");";
*/
            }
          //  OP_stmt=OP_Conn.prepareStatement(OP_Query);
            OP_stmt.executeBatch();
            OP_Conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
}
/********************************************************************** 5.6 Pilot Cover Product **********************************************************************************************************************************************************************************************************/
public boolean GetPilotCoverProductOnDate(String Assign_date) {
    Connection DMS_Conn=createDMSConnection();
    Connection OP_Conn=createOperationConnection();
    PreparedStatement DMS_stmt=null;
    PreparedStatement OP_stmt=null;
    ResultSet DMS_rs=null;
    String DMS_Query="";
    String OP_Query="";
    int num_records=0;
    DMS_Query="select p_cov.\"ID\",cov.\"DRIVER_ID\" , p_cov.\"Pilot_ID\" , cov.\"PRODUCT_ID\",cov.\"Treatment_ID\",p_cov.\"Cover_Driver_ID\",p_cov.\"Assign_Date\",\"Quantity\" , p_cov.\"Current_Quantity\" , p_cov.\"Dirty_Quantity\" , p_cov.\"Actual_Clean\" , p_cov.\"Actual_Dirty\" ,  \n" + 
    "    p_cov.\"Approved_clean\" , p_cov.\"Approved_Dirty\" , p_cov.\"Driver_Accept\" , p_cov.\"DIFF_CLEAN\" , p_cov.\"DIFF_DIRTY\" , p_cov.\"Area_ID\" , p_cov.\"F_Delivered_Clean\" , p_cov.\"F_Delivered_Dirty\" ,  \n" + 
    "    p_cov.\"MANUAL_NEW\" , p_cov.\"MANUAL_CANCEL\" , p_cov.\"LOST\" , p_cov.\"R_LOST\" , p_cov.\"NEW_ACTUAL\" , p_cov.\"CANCEL_ACTUAL\"\n" + 
    "    FROM \"Final_Pilot_Cover\" p_cov\n" + 
    "	left join \"COVER_PRODUCT\" cov on cov.\"ID\"=\"Cover_Driver_ID\"\n" + 
    "	where \"Assign_Date\"::date='"+Assign_date+"'";
        try {
            DMS_stmt = DMS_Conn.prepareStatement(DMS_Query);
            DMS_rs=DMS_stmt.executeQuery();
            OP_Query+="insert into pilot_cover_product(id,pilot_id,cover_driver_id,assign_date,quantity,current_quantity,dirty_quantity,actual_clean,actual_dirty,approved_clean,approved_dirty,driver_accept,diff_clean,diff_dirty,area_id,f_delivered_clean,f_delivered_dirty,manual_new,manual_cancel,lost,rlost,actual_new,actual_cancel,product_id,treatment_id,driver_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
OP_stmt=OP_Conn.prepareStatement(OP_Query);
            while(DMS_rs.next()) {
                num_records++;
                OP_stmt.setObject(1, DMS_rs.getObject(DMS_rs.findColumn("ID")));
                OP_stmt.setObject(2, DMS_rs.getObject(DMS_rs.findColumn("Pilot_ID")));
                OP_stmt.setObject(3, DMS_rs.getObject(DMS_rs.findColumn("Cover_Driver_ID")));
                OP_stmt.setObject(4, DMS_rs.getObject(DMS_rs.findColumn("Assign_Date")));
                OP_stmt.setObject(5, DMS_rs.getObject(DMS_rs.findColumn("Quantity")));
                OP_stmt.setObject(6, DMS_rs.getObject(DMS_rs.findColumn("Current_Quantity")));
                OP_stmt.setObject(7, DMS_rs.getObject(DMS_rs.findColumn("Dirty_Quantity")));
                OP_stmt.setObject(8, DMS_rs.getObject(DMS_rs.findColumn("Actual_Clean")));
                OP_stmt.setObject(9, DMS_rs.getObject(DMS_rs.findColumn("Actual_Dirty")));
                OP_stmt.setObject(10, DMS_rs.getObject(DMS_rs.findColumn("Approved_clean")));
                OP_stmt.setObject(11, DMS_rs.getObject(DMS_rs.findColumn("Approved_Dirty")));
                OP_stmt.setObject(12, DMS_rs.getObject(DMS_rs.findColumn("Driver_Accept")));
                OP_stmt.setObject(13, DMS_rs.getObject(DMS_rs.findColumn("DIFF_CLEAN")));
                OP_stmt.setObject(14, DMS_rs.getObject(DMS_rs.findColumn("DIFF_DIRTY")));
                OP_stmt.setObject(15, DMS_rs.getObject(DMS_rs.findColumn("Area_ID")));
                OP_stmt.setObject(16, DMS_rs.getObject(DMS_rs.findColumn("F_Delivered_Clean")));
                OP_stmt.setObject(17, DMS_rs.getObject(DMS_rs.findColumn("F_Delivered_Dirty")));
                OP_stmt.setObject(18, DMS_rs.getObject(DMS_rs.findColumn("MANUAL_NEW")));
                OP_stmt.setObject(19,DMS_rs.getObject(DMS_rs.findColumn("CANCEL_ACTUAL")));
                OP_stmt.setObject(20, DMS_rs.getObject(DMS_rs.findColumn("LOST")));
                OP_stmt.setObject(21, DMS_rs.getObject(DMS_rs.findColumn("R_LOST")));
                OP_stmt.setObject(22, DMS_rs.getObject(DMS_rs.findColumn("NEW_ACTUAL")));
                OP_stmt.setObject(23, DMS_rs.getObject(DMS_rs.findColumn("CANCEL_ACTUAL")));
                OP_stmt.setObject(24, DMS_rs.getObject(DMS_rs.findColumn("PRODUCT_ID")));
                OP_stmt.setObject(25, DMS_rs.getObject(DMS_rs.findColumn("Treatment_ID")));
                OP_stmt.setObject(26, DMS_rs.getObject(DMS_rs.findColumn("DRIVER_ID")));
                OP_stmt.addBatch();

              /*  OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("ID"))+",";
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("Pilot_ID"))+"' , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Cover_Driver_ID"))+" , ";
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("Assign_Date"))+"' , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Quantity"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Current_Quantity"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Dirty_Quantity"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Actual_Clean"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Actual_Dirty"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Approved_clean"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Approved_Dirty"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Driver_Accept"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("DIFF_CLEAN"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("DIFF_DIRTY"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Area_ID"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("F_Delivered_Clean"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("F_Delivered_Dirty"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("MANUAL_NEW"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("MANUAL_CANCEL"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("LOST"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("R_LOST"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("NEW_ACTUAL"))+" , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("CANCEL_ACTUAL"))+" , ";
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("PRODUCT_ID"))+"' , ";
                OP_Query+=""+DMS_rs.getObject(DMS_rs.findColumn("Treatment_ID"))+" , ";
                OP_Query+="'"+DMS_rs.getObject(DMS_rs.findColumn("DRIVER_ID"))+"' ); ";
*/
            }
            if(num_records>0) {
                System.out.println(OP_Query);
               // OP_stmt=OP_Conn.prepareStatement(OP_Query);
                OP_stmt.executeBatch();
                OP_Conn.commit();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
}

public boolean GetCloudAssignments() {
    Connection op_conn=createOperationConnection();
    Connection DMS_conn=createDMSConnection();
    String Query="SELECT \"ID\" ,COALESCE(\"CONTRACT_STATUS\",1) AS \"CONTRACT_STATUS\" ,COALESCE(\"ASSIGNMENTS_WORK_XY\".\"CLOSE_CODE\",1) AS \"CLOSE_CODE\",\n" + 
    "             COALESCE(\"ASSIGNMENTS_WORK_XY\".\"CLOSE_CODE_REASON\",'Not Processed') AS \"CLOSE_CODE_REASON\" , \"GEOCODE_X\" , \"GEOCODE_Y\" , \n" + 
    "                                 COALESCE( \"CUSTOMER_NOTE\", \"CUSTOMER_NOTES\") as \"CUSTOMER_NOTE\" , COALESCE(to_char(\"ACTION_DATE\" ,'DD-MON-YY HH24:mi:SS'),to_char(\"ASSIGN_DATE\" ,'DD-MON-YY HH24:mi:SS')) as\"ACTION_DATE\", \"RECIEPT_NO\" , \n" + 
    "                                     \"TOTAL_PRICE_PAID\" ,\"ASSIGNMENTS_WORK_XY\" . \"CARD_NO\" ,  \n" + 
    "                                     \"PILOT_ID\" ,\"DRIV_ID\" ,\"ASSIGN_ID\" ,\"ASSIGNMENTS_WORK_XY\" .\"AID\" ,\n" + 
    "                                    \"CLOSE_CODE_CANCEL\" ,\"CANCEL_CLOSE_CODE_REASON\",\"ASSIGNMENT_TYPE\",\n" + 
    "                                      \"SPARE_ID\",\"Duration\",\"ACTUAL_PAID\",\"CUSTOMER_TYPE\" ,\"PRIORITY\"  ,\"ASSIGNMENTS_WORK_XY\".\"AREA_ID\" , right(\"ASSIGNMENTS_WORK_XY\".\"FU_Note\",300) AS FU_Note  , RIGHT(\"ASSIGNMENTS_WORK_XY\".\"TICKETING_NOTES\" ,500) AS TICKETING_NOTES ,\"ASSIGNMENTS_WORK_XY\".\"PERFORMER\" \n" + 
    "                                       FROM \"ASSIGNMENTS_WORK_XY\"  \n" + 
    "                                              LEFT JOIN \"ASSIGNED_CONTRACT_D\" ON \"ASSIGNMENTS_WORK_XY\".\"AID\" = \"ASSIGNED_CONTRACT_D\".\"AID\" \n" + 
    "                                         where \"ASSIGN_DATE\" ::date='2020-07-21' \n" + 
    "                                          AND (\"ID\" IS NULL OR  \"ID\" IN (SELECT MAX(\"ID\") FROM \"ASSIGNED_CONTRACT_D\" ,\"ASSIGNMENTS_WORK_XY\"  \n" + 
    "                                                       WHERE \"ACTION_DATE\"::DATE='2020-07-21'  AND \"ASSIGNED_CONTRACT_D\".\"AID\" = \"ASSIGNMENTS_WORK_XY\".\"AID\"  \n" + 
    "                                              AND \"ASSIGNMENTS_WORK_XY\".\"ASSIGNMENT_TYPE\" != 4 GROUP BY \"ASSIGNED_CONTRACT_D\".\"CARD_NO\"))\n" + 
   // "                                     and (\"Oracle_flag\" !=1 or \"Oracle_flag\"  isnull) \n" + 
    //  "                                    // and\"ASSIGNMENTS_WORK_XY\".\"AREA_ID\"=\"+Area_ID+\"\\n\" + \n" +
    // "                                    //     and\"ASSIGNMENTS_WORK_XY\".\"DRIV_ID\"='\"+Driver_id+\"'\n" +
    "                                           and\"ASSIGNMENTS_WORK_XY\".\"CHANGED_STATE\" IN (0,1) and \"ASSIGNMENTS_WORK_XY\".\"ASSIGNMENT_TYPE\" !=4 \n";
    ResultSet rs=null;
    PreparedStatement ps;
        try {
            ps = DMS_conn.prepareStatement(Query);
            rs=ps.executeQuery();
            String res="";
            while(rs.next())
            {
                res+="insert into assignments(assign_id,cloud_id,contract_status,close_code,close_code_reason," +
                    "geocode_x,geocode_y,assign_date,c_r_no,total_price_paid,card_no,driver_id,pilot_id," +
                    "close_code,close_code_reason,spare_id,assignment_type,actual_paid" +
                    ",customer_note,customer_type,area_id,priority,fu_note,ticketing_note,performer)\n";
                res+="values(";
                res+="'"+rs.getString(rs.findColumn("ASSIGN_ID"))+"'";
                res+=","+rs.getInt(rs.findColumn("AID"));
                res+=","+rs.getInt(rs.findColumn("CONTRACT_STATUS"));
                res+=","+rs.getInt(rs.findColumn("CLOSE_CODE"));
                res+=",'"+rs.getString(rs.findColumn("CLOSE_CODE_REASON"))+"'";
                res+=",'"+rs.getString(rs.findColumn("GEOCODE_X"))+"'";
                res+=",'"+rs.getString(rs.findColumn("GEOCODE_Y"))+"'";
                res+=",'"+rs.getString(rs.findColumn("ACTION_DATE"))+"'";
                res+=","+rs.getInt(rs.findColumn("TOTAL_PRICE_PAID"));
                res+=",'"+rs.getString(rs.findColumn("CARD_NO"))+"'";
                res+=",'"+rs.getString(rs.findColumn("DRIV_ID"))+"'";
                res+=",'"+rs.getObject(rs.findColumn("PILOT_ID"))+"'";
                res+=","+rs.getInt(rs.findColumn("CLOSE_CODE_CANCEL"))+"";
                res+=",'"+rs.getString(rs.findColumn("CANCEL_CLOSE_CODE_REASON"))+"'";
                res+=",'"+rs.getString(rs.findColumn("SPARE_ID"))+"'";
                res+=","+rs.getObject(rs.findColumn("ASSIGNMENT_TYPE"))+"";
                res+=","+rs.getObject(rs.findColumn("ACTUAL_PAID"))+"";
                if(rs.getObject(rs.findColumn("CUSTOMER_NOTE"))!=null)
                res+=",'"+rs.getObject(rs.findColumn("CUSTOMER_NOTE")).toString().replaceAll("'", "")+"'";
                else
                    res+=","+rs.getObject(rs.findColumn("CUSTOMER_NOTE"))+"";
                if(rs.getString(rs.findColumn("CUSTOMER_TYPE"))!=null)
                res+=",'"+rs.getObject(rs.findColumn("CUSTOMER_TYPE"))+"'";
                else
                    res+=","+rs.getString(rs.findColumn("CUSTOMER_TYPE"))+"";
                res+=",'"+rs.getString(rs.findColumn("AREA_ID"))+"'";
            
                res+=","+rs.getObject(rs.findColumn("PRIORITY"))+"";
                if(rs.getObject(rs.findColumn("FU_NOTE"))!=null)
                res+=",'"+rs.getObject(rs.findColumn("FU_NOTE")).toString().replaceAll("'", "")+"'";
                else
                    res+=","+rs.getObject(rs.findColumn("FU_NOTE"))+"";
                
                if(rs.getObject(rs.findColumn("TICKETING_NOTES"))!=null)
                res+=",'"+rs.getObject(rs.findColumn("TICKETING_NOTES")).toString().replaceAll("'", "")+"'";
                else
                    res+=","+rs.getObject(rs.findColumn("TICKETING_NOTES"))+"";
                res+=","+rs.getObject(rs.findColumn("PERFORMER"))+"";
                res+=");";
              
            }
        PreparedStatement ps1=op_conn.prepareStatement(res);
            ps1.executeQuery();
            DMS_conn.close();
            op_conn.close();
        } catch (SQLException e) {
        }
        return true;
}
/************************************************************************ Send SMS In Team Leader Screen ************************************************************************************************/
public Boolean SendSMS(String ids , String agent) {
    Connection Op_conn=createOperationConnection();
    PreparedStatement operation_stmt=null;
    Date date= new Date();
    Timestamp ts = new Timestamp(date.getTime());
    String update_query="UPDATE daily_work set sms_flag=1 , send_by ='"+agent+"' , send_time=SYSDATE where id in ("+ids+")";
        try {
            operation_stmt = Op_conn.prepareStatement(update_query);
            operation_stmt.executeUpdate();
            Op_conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
}
public Boolean Adding_Scheduling(String work_date) {
    Connection op_conn=createOperationConnection();
    PreparedStatement operation_stmt=null;
    String insert_query="";
    PreparedStatement insert_stmt=null;
    String select_query="select TO_CHAR( WORKING_DATE, 'DD-MON-YYYY' ) working_date ,day_,area_id,area_name,out_of_schedule,include_exclude,scheduled,total_contracts,perfect_issue,b_over,lost,b_count,selected_contracts,zero_b,total_contracts_lost,need_call, priority_8,priority_9,priority_10,perfect_issue_bover,dispatchable,WEEK_1_CONTRACTS, ISSUE_BOVER,PERFECT,WEEK_2_CONTRACTS ,WEEK_3_CONTRACTS from scheduling_summary where working_date =TO_DATE('"+work_date+"','YYYY-MM-DD') and TOTAL_CONTRACTS > 0 \n" + 
    "and area_id not in (select area_id from scheduling_table where working_date=TO_DATE('"+work_date+"','YYYY-MM-DD'))\n";       
    
        try {
            operation_stmt = op_conn.prepareStatement(select_query);
            ResultSet rs=operation_stmt.executeQuery();
            System.out.println("select executed");
            System.out.println(rs);
            insert_query+="insert into scheduling_table(area_id,area_name,DAY_NAME,total_contracts,perfect_issue_contracts,bover,lost,b_count,selected_contracts,zerob_contracts,total_contracts_lost,need_call_contracts,priority_8_contracts,priority_9_contracts,priority_10_contracts,perfect_issue_bover_contracts,dispatchable_contracts,week_1_contracts,week_2_contracts,week_3_contracts,include_exclude,out_of_schedule,scheduled,working_date,ISSUE_BOVER,PERFECT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
insert_stmt=op_conn.prepareStatement(insert_query);
            while(rs.next()) {
                System.out.println("record");
                insert_stmt.setObject(1, rs.getObject(rs.findColumn("area_id")));
                insert_stmt.setObject(2, rs.getObject(rs.findColumn("day_")));
                insert_stmt.setObject(3, rs.getObject(rs.findColumn("area_name")));
                insert_stmt.setObject(4, rs.getObject(rs.findColumn("total_contracts")));
                insert_stmt.setObject(5, rs.getObject(rs.findColumn("perfect_issue")));
                insert_stmt.setObject(6, rs.getObject(rs.findColumn("b_over")));
                insert_stmt.setObject(7, rs.getObject(rs.findColumn("lost")));
                insert_stmt.setObject(8, rs.getObject(rs.findColumn("b_count")));
                insert_stmt.setObject(9, rs.getObject(rs.findColumn("selected_contracts")));
                insert_stmt.setObject(10, rs.getObject(rs.findColumn("zero_b")));
                insert_stmt.setObject(11, rs.getObject(rs.findColumn("total_contracts_lost")));
                insert_stmt.setObject(12, rs.getObject(rs.findColumn("need_call")));
                insert_stmt.setObject(13, rs.getObject(rs.findColumn("priority_8")));
                insert_stmt.setObject(14, rs.getObject(rs.findColumn("priority_9")));
                insert_stmt.setObject(15, rs.getObject(rs.findColumn("priority_10")));
                insert_stmt.setObject(16, rs.getObject(rs.findColumn("perfect_issue_bover")));
                insert_stmt.setObject(17, rs.getObject(rs.findColumn("dispatchable")));
                insert_stmt.setObject(18, rs.getObject(rs.findColumn("WEEK_1_CONTRACTS")));
                insert_stmt.setObject(19, rs.getObject(rs.findColumn("WEEK_2_CONTRACTS")));
                insert_stmt.setObject(20, rs.getObject(rs.findColumn("WEEK_3_CONTRACTS")));
                insert_stmt.setObject(21, rs.getObject(rs.findColumn("include_exclude")));
                insert_stmt.setObject(22, rs.getObject(rs.findColumn("out_of_schedule")));
                insert_stmt.setObject(23, 0);
                insert_stmt.setObject(24, rs.getObject(rs.findColumn("working_date")));
                insert_stmt.setObject(25, rs.getObject(rs.findColumn("ISSUE_BOVER")));
                insert_stmt.setObject(26, rs.getObject(rs.findColumn("PERFECT")));

               insert_stmt.addBatch();
            }
            System.out.println(insert_query);
            insert_stmt.executeBatch();
            op_conn.commit();
          /* insert_stmt=op_conn.prepareStatement(insert_query);
            insert_stmt.execute();
            op_conn.commit();*/
            rs.close();
            operation_stmt.close();
            insert_stmt.close();
                       
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
}

/**************************************************** Payroll Functions ****************************************************************************************/
 public boolean DownloadPayrollEmp_order_count()
    {
        String res="";
          
            Connection Postgre=createDMSConnection();
         /*   String Query="SELECT \"ASSIGN_DATE\",\"USER_NAME\",MIN(\"USERGROUP_ID\") AS \"USERGROUP_ID\",MIN(\"USER_TYPE\") AS \"USER_TYPE\",\n" + 
            " MIN(\"ALL_TOTAL_ASSIGNED\") AS \"ALL_TOTAL_ASSIGNED\",MIN(\"B_TOTAL_ASSIGNED\") AS \"B_TOTAL_ASSIGNED\",\n" + 
            " MIN(\"TOTAL_DONE\") AS \"TOTAL_DONE\",MIN(\"B_TOTAL_DONE\") AS \"B_TOTAL_DONE\",MIN(\"TOTAL_NOT_DONE\") AS \"TOTAL_NOT_DONE\",\n" + 
            " MIN(\"TOTAL_PARTIAL\") AS \"TOTAL_PARTIAL\",MIN(\"TOTAL_PENDING\") AS \"TOTAL_PENDING\",SUM(\"ACTUAL_PAID\") AS \"ACTUAL_PAID\" , \n" + 
" min(\"TOTAL_99\") AS \"TOTAL_99\" , MIN(\"TOTAL_CANCEL\") AS \"TOTAL_CANCEL\" , MIN(\"TOTAL_FREE\") AS \"TOTAL_FREE\" \n"+
            " FROM \n" + 
            "(SELECT \"v_Delivery_Payroll_P_PD\".*,COALESCE(\"ACTUAL_PAID_AID\".\"ACTUAL_PAID\",0) AS \"ACTUAL_PAID\"  FROM \"v_Delivery_Payroll_P_PD\"\n" + 
            "LEFT JOIN (SELECT SUM(\"ACTUAL_PAID\") AS \"ACTUAL_PAID\",\"ASSIGNED_CONTRACT_D\".\"AID\",\"ACTION_DATE\"::DATE AS \"ACTION_DATE\" ,\"PILOT_ID\",\"DRIV_ID\"\n" + 
            "		   FROM \"ASSIGNED_CONTRACT_D\",\"ASSIGNMENTS_WORK_XY\" WHERE\n" + 
            "		   \"ASSIGNED_CONTRACT_D\".\"AID\"=\"ASSIGNMENTS_WORK_XY\".\"AID\"\n" + 
            "		   AND \"ACTUAL_PAID\">0\n" + 
            "		   AND \"ASSIGNMENTS_WORK_XY\".\"CHANGED_STATE\" IN(0,1)\n" + 
            "		   GROUP BY \"ASSIGNED_CONTRACT_D\".\"AID\",\"ACTION_DATE\"::DATE,\"PILOT_ID\",\"DRIV_ID\") AS \"ACTUAL_PAID_AID\"\n" + 
            "		   ON ((\"v_Delivery_Payroll_P_PD\".\"USERGROUP_ID\"=2 \n" + 
            "			   AND \"ACTUAL_PAID_AID\".\"PILOT_ID\" =\"v_Delivery_Payroll_P_PD\".\"USER_NAME\")\n" + 
            "			   OR (\"v_Delivery_Payroll_P_PD\".\"USERGROUP_ID\"!=2\n" + 
            "				   AND \"ACTUAL_PAID_AID\".\"DRIV_ID\" =\"v_Delivery_Payroll_P_PD\".\"USER_NAME\" ))\n" + 
            "		   AND \"v_Delivery_Payroll_P_PD\".\"ASSIGN_DATE\"=\"ACTUAL_PAID_AID\".\"ACTION_DATE\") AS \"v_Delivery_Payroll_P_PD\"\n" + 
            "		   GROUP BY \"ASSIGN_DATE\",\"USER_NAME\" ";*/
            
            String Query="SELECT \"ASSIGN_DATE\",\"USER_NAME\",MIN(\"GROUP_ID\") AS \"USERGROUP_ID\",MIN(\"GROUP_NAME\") AS \"USER_TYPE\",   \n" + 
            "                        MIN(\"TOTAL_ASSIGNED\") AS \"ALL_TOTAL_ASSIGNED\",MIN(\"TOTAL_B_ASSIGNED\") AS \"B_TOTAL_ASSIGNED\",   \n" + 
            "                        MIN(\"TOTAL_DONE\") AS \"TOTAL_DONE\",MIN(\"B_TOTAL_DONE\") AS \"B_TOTAL_DONE\",MIN(\"TOTAL_NOT_DONE\") AS \"TOTAL_NOT_DONE\",   \n" + 
            "                         MIN(\"TOTAL_PARTIAL\") AS \"TOTAL_PARTIAL\",MIN(\"TOTAL_PENDING\") AS \"TOTAL_PENDING\",SUM(\"ACTUAL_PAID\") AS \"ACTUAL_PAID\" ,    \n" + 
            "             min(\"TOTAL_99\") AS \"TOTAL_99\" , MIN(\"TOTAL_CANCEL\") AS \"TOTAL_CANCEL\" , MIN(\"TOTAL_FREE\") AS \"TOTAL_FREE\"   , \"DRIVER_ID\"\n" + 
            "                         FROM    \n" + 
            "                       (SELECT \"v_Delivery_Payroll_P_PD\".*,COALESCE(\"ACTUAL_PAID_AID\".\"ACTUAL_PAID\",0) AS \"ACTUAL_PAID\"  FROM \"v_Delivery_Payroll_P_PD\"   \n" + 
            "                       LEFT JOIN (SELECT SUM(\"ACTUAL_PAID\") AS \"ACTUAL_PAID\",\"ASSIGNED_CONTRACT_D\".\"AID\",\"ACTION_DATE\"::DATE AS \"ACTION_DATE\" ,\"PILOT_ID\",\"DRIV_ID\"   \n" + 
            "                      		   FROM \"ASSIGNED_CONTRACT_D\",\"ASSIGNMENTS_WORK_XY\" WHERE   \n" + 
            "                       		   \"ASSIGNED_CONTRACT_D\".\"AID\"=\"ASSIGNMENTS_WORK_XY\".\"AID\"   \n" + 
            "                       		   AND \"ACTUAL_PAID\">0   \n" + 
            "                       		   AND \"ASSIGNMENTS_WORK_XY\".\"CHANGED_STATE\" IN(0,1)   \n" + 
            "                        		   GROUP BY \"ASSIGNED_CONTRACT_D\".\"AID\",\"ACTION_DATE\"::DATE,\"PILOT_ID\",\"DRIV_ID\") AS \"ACTUAL_PAID_AID\"   \n" + 
            "                        		   ON ((\"v_Delivery_Payroll_P_PD\".\"GROUP_ID\"=2    \n" + 
            "                        			   AND \"ACTUAL_PAID_AID\".\"PILOT_ID\" =\"v_Delivery_Payroll_P_PD\".\"USER_NAME\")   \n" + 
            "                        			   OR (\"v_Delivery_Payroll_P_PD\".\"GROUP_ID\"!=2   \n" + 
            "                        				   AND \"ACTUAL_PAID_AID\".\"DRIV_ID\" =\"v_Delivery_Payroll_P_PD\".\"USER_NAME\" ))   \n" + 
            "                        		   AND \"v_Delivery_Payroll_P_PD\".\"ASSIGN_DATE\"=\"ACTUAL_PAID_AID\".\"ACTION_DATE\") AS \"v_Delivery_Payroll_P_PD\"   \n" + 
            "                       		   GROUP BY \"ASSIGN_DATE\",\"USER_NAME\", \"DRIVER_ID\"";
              PreparedStatement pstmt = null;
             PreparedStatement pstmt2 = null;
        String tmp_ass="";
              ResultSet rs=null;
            if(Postgre!=null)
            {
             try {
                // System.out.println(Query);
                 pstmt2 = Postgre.prepareStatement(Query);
                // pstmt2.setDate(1, Action_Date);
                // pstmt2.setString(1, Area_ID);
                 //pstmt2.setString(2, Driver_id);


                 rs = pstmt2.executeQuery();
                
                String AID_Ass="";
                 res+="";
                 Connection Oracle=createOperationConnection();
                 res+="INSERT INTO  PILOT_DELIVERY(\n" + 
                 "  USER_NAME,\n" + 
                 "  ASSIGN_DATE,\n" + 
                 "  GROUP_ID,\n" + 
                 "  GROUP_NAME,\n" + 
                 "  TOTAL_ASSIGNED,\n" + 
                 "  TOTAL_B_ASSIGNED,\n" + 
                 "  TOTAL_DONE,\n" + 
                 "  TOTAL_B_DONE,\n" + 
                 "  TOTAL_NOT_DONE_99,\n" + 
                 "  TOTAL_PARTIAL,\n" + 
                 "  TOTAL_PENDING_99," +
                     "DONE_CONTRACT_LE,TOTAL_CANCEL,TOTAL_FREE,TOTAL_99,DRIVER_ID)";
                 //res+=" SELECT ";
                 res+=" VALUES( ?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                 pstmt = Oracle.prepareStatement(res);
                 while(rs.next())
                 {
                     
                     pstmt.setObject(1, rs.getString(rs.findColumn("USER_NAME")));
                     pstmt.setObject(2, rs.getString(rs.findColumn("ASSIGN_DATE")));
                     pstmt.setObject(3, rs.getInt(rs.findColumn("USERGROUP_ID")));
                     pstmt.setObject(4, rs.getString(rs.findColumn("USER_TYPE")));
                     pstmt.setObject(5, rs.getInt(rs.findColumn("ALL_TOTAL_ASSIGNED")));
                     pstmt.setObject(6, rs.getInt(rs.findColumn("B_TOTAL_ASSIGNED")));
                     pstmt.setObject(7, rs.getInt(rs.findColumn("TOTAL_DONE")));
                     pstmt.setObject(8, rs.getInt(rs.findColumn("B_TOTAL_DONE")));
                     pstmt.setObject(9, rs.getInt(rs.findColumn("TOTAL_NOT_DONE")));
                     pstmt.setObject(10, rs.getInt(rs.findColumn("TOTAL_PARTIAL")));
                     pstmt.setObject(11, rs.getInt(rs.findColumn("TOTAL_PENDING")));
                     pstmt.setObject(12, rs.getInt(rs.findColumn("ACTUAL_PAID")));
                     pstmt.setObject(13, rs.getInt(rs.findColumn("TOTAL_CANCEL")));
                     pstmt.setObject(14, rs.getInt(rs.findColumn("TOTAL_FREE")));
                     pstmt.setObject(15, rs.getInt(rs.findColumn("TOTAL_99")));
                     pstmt.setObject(16, rs.getInt(rs.findColumn("DRIVER_ID")));


                     pstmt.addBatch(); 
                                    
                   
                 }
                 
                 // INSERT INTO ORACLE
                
                 if(Oracle!=null)
                 {
                    
                     try{
                         pstmt.executeBatch();
                         Oracle.commit();
                     }
                     catch(SQLException e)
                     {e.printStackTrace();}
                }
                 if(pstmt!=null)
                 pstmt.close();
                 if(Oracle!=null)
                 Oracle.close();
                
              
             } catch (SQLException e) {
                 e.printStackTrace();
                 if(pstmt!=null)

                         try {
                             pstmt.close();
                             rs.close();
                         } catch (SQLException f) {
                             f.printStackTrace();
                         }

                     if(pstmt2!=null)
                         try {
                             pstmt2.close();
                         } catch (SQLException k) {
                             k.printStackTrace();
                         }
                   
                 if(Postgre!=null)
                     try {
                         Postgre.close();
                     } catch (SQLException k) {
                         k.printStackTrace();
                     }
                 
                return false;
                 
             }
             
            
             if(pstmt!=null)
                 try {
                     pstmt.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             if(pstmt2!=null)
                     try {
                         pstmt2.close();
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                
             if(Postgre!=null)
                 try {
                     Postgre.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             return true;
         }
            else
            {
               
                if(Postgre!=null)
                {
                 try {
                     Postgre.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         }

            return true;
         
    }
    
    ///
    boolean DownloadPayrollDriver_order_count() {
        String res="";
          
            Connection Postgre=createDMSConnection();
            /*       String Query="SELECT \"USER_NAME\", \"USERGROUP_ID\", \"ASSIGN_DATE\", \"TOTAL_ASSIGNED\", \"TOTAL_DONE_BY_ME\", \"TOTAL_DONE_BY_PILOTS\", \"TOTAL_ASSIGNED_B\", \"TOTAL_PARTIAL_BY_PILOTS\", \"TOTAL_NOT_DONE\", \"TOTAL_PENDING\", \"TOTAL_DONE_VALUE\", \"AREA_ID\", \"ZONE_ID\", \"D_IN_OUT\", \"D_IN_OUT_multiply_0.675\" , \"TOTAL_99\" , \"TOTAL_FREE\" , \"TOTAL_CANCEL\" , \"TOTAL_DONE_PILOTS_VALUE\" , \"TOTAL_ASSIGNED_P\"    \n" + 
            "	FROM public.\"v_Delivery_Payroll_D\" "; */
            String Query="SELECT \"USER_NAME\", \"GROUP_ID\" \"USERGROUP_ID\", \"ASSIGN_DATE\", \"TOTAL_ASSIGNED\",\"CONTRACT_DONE_LE\" \"TOTAL_DONE_BY_ME\", \"TOTAL_DONE_BY_PILOTS\", \"TOTAL_ASSIGNED_B\", \"TOTAL_PARTIAL\" \"TOTAL_PARTIAL_BY_PILOTS\", \"TOTAL_NOT_DONE\", \"TOTAL_PENDING\", \"CONTRACT_DONE_LE\" \"TOTAL_DONE_VALUE\", \"AREA_ID\", \"ZONE_ID\", \"D_IN_OUT\", \"D_IN_OUT_multiply_0.675\" , \"TOTAL_99\" , \"TOTAL_FREE\" , \"TOTAL_CANCEL\" ,\"PILOT_DONE_CONTRACT_LE\" \"TOTAL_DONE_PILOTS_VALUE\" ,\"TOTAL_ASSIGNED_TO_PILOTS\" \"TOTAL_ASSIGNED_P\"   \n" + 
            "            	FROM \"v_Delivery_Payroll_D\"";
              PreparedStatement pstmt = null;
             PreparedStatement pstmt2 = null;
        String tmp_ass="";
              ResultSet rs=null;
            if(Postgre!=null)
            {
             try {
                // System.out.println(Query);
                 pstmt2 = Postgre.prepareStatement(Query);
                // pstmt2.setDate(1, Action_Date);
                // pstmt2.setString(1, Area_ID);
                 //pstmt2.setString(2, Driver_id);


                 rs = pstmt2.executeQuery();
                
                       String ID_Ass="";
                 res+="";
                 Connection Oracle=createOperationConnection();
                 res+="INSERT INTO DRIVER_DELIVERY(\n" + 
                 "  USER_NAME,\n" + 
                 "  ASSIGN_DATE,\n" + 
                 "  TOTAL_ASSIGNED,\n" + 
                 "  TOTAL_ASSIGNED_B,\n" + 
                 "  TOTAL_DONE_BY_DRIVER,\n" + 
                 "  TOTAL_DONE_BY_PILOTS,\n" + 
                 "  TOTAL_PARTIAL,\n" + 
                 "  TOTAL_NOT_DONE,\n" + 
                 "  TOTAL_PENDING,\n" + 
                 "  CONTRACT_DONE_LE,\n" + 
                 "  AREA_ID,\n" + 
                 "  ZONE_ID,\n" + 
                 "  KM_IN_OUT,\n" + 
                 "  KM_IN_OUT_MULTIPLE_,TOTAL_DONE,\n"+
"TOTAL_CANCEL,\n"+
"TOTAL_FREE,\n"+
"TOTAL_99,\n"+
"PILOT_DONE_CONTRACT_LE  , TOTAL_ASSIGNED_PILOTS )\n" + 
                 "  VALUES( ?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                 //res+=" SELECT ";
                 pstmt = Oracle.prepareStatement(res);
                 while(rs.next())
                 {
                     
                     pstmt.setObject(1, rs.getString(rs.findColumn("USER_NAME")));
                     pstmt.setObject(2, rs.getString(rs.findColumn("ASSIGN_DATE")));
                     pstmt.setObject(3, rs.getInt(rs.findColumn("TOTAL_ASSIGNED")));
                     pstmt.setObject(4, rs.getInt(rs.findColumn("TOTAL_ASSIGNED_B")));
                     pstmt.setObject(5, rs.getInt(rs.findColumn("TOTAL_DONE_BY_ME")));
                     pstmt.setObject(6, rs.getInt(rs.findColumn("TOTAL_DONE_BY_PILOTS")));
                     pstmt.setObject(7, rs.getInt(rs.findColumn("TOTAL_PARTIAL_BY_PILOTS")));
                     pstmt.setObject(8, rs.getInt(rs.findColumn("TOTAL_NOT_DONE")));
                     pstmt.setObject(9, rs.getInt(rs.findColumn("TOTAL_PENDING")));
                     pstmt.setObject(10, rs.getInt(rs.findColumn("TOTAL_DONE_VALUE")));
                     pstmt.setObject(11, rs.getInt(rs.findColumn("AREA_ID")));
                     pstmt.setObject(12, rs.getInt(rs.findColumn("ZONE_ID")));
                     pstmt.setObject(13, rs.getInt(rs.findColumn("D_IN_OUT")));
                     pstmt.setObject(14, rs.getInt(rs.findColumn("D_IN_OUT_multiply_0.675")));
pstmt.setObject(15, rs.getInt(rs.findColumn("TOTAL_DONE_BY_PILOTS")));
pstmt.setObject(16, rs.getInt(rs.findColumn("TOTAL_CANCEL")));
pstmt.setObject(17, rs.getInt(rs.findColumn("TOTAL_FREE")));
pstmt.setObject(18, rs.getInt(rs.findColumn("TOTAL_99")));
pstmt.setObject(19, rs.getInt(rs.findColumn("TOTAL_DONE_PILOTS_VALUE")));
                     pstmt.setObject(20, rs.getInt(rs.findColumn("TOTAL_ASSIGNED_P")));
  
                     
                     pstmt.addBatch(); 
                                    
                   
                 }
                 
                 // INSERT INTO ORACLE
                
                 if(Oracle!=null)
                 {
                    
                     try{
                         pstmt.executeBatch();
                         Oracle.commit();
                     }
                     catch(SQLException e)
                     {e.printStackTrace();}
                }
                 if(pstmt!=null)
                 pstmt.close();
                 if(Oracle!=null)
                 Oracle.close();
                
              
             } catch (SQLException e) {
                 e.printStackTrace();
                 if(pstmt!=null)

                         try {
                             pstmt.close();
                             rs.close();
                         } catch (SQLException f) {
                             f.printStackTrace();
                         }

                     if(pstmt2!=null)
                         try {
                             pstmt2.close();
                         } catch (SQLException k) {
                             k.printStackTrace();
                         }
                   
                 if(Postgre!=null)
                     try {
                         Postgre.close();
                     } catch (SQLException k) {
                         k.printStackTrace();
                     }
                 
                return false;
                 
             }
             
            
             if(pstmt!=null)
                 try {
                     pstmt.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             if(pstmt2!=null)
                     try {
                         pstmt2.close();
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                
             if(Postgre!=null)
                 try {
                     Postgre.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             return true;
         }
            else
            {
               
                if(Postgre!=null)
                {
                 try {
                     Postgre.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         }

            return true;
    }
    
    
    /*********************************************** Move Data Daily From Local Operation  To Cloud Operation ***************************************/
    // Upload New Added Contracts On Operation Local System With Operation Flag=1 and Cloud Flag is null 
public Boolean UploadNewContractsFromOperationToOperationCloud() {
    /**************************** Upload New Contracts ************************************************************************/
    Connection Loperation_Conn=createOldOperationConnection();
    Connection Cloud_Operation_Conn=createOperationConnection();
    PreparedStatement LPstmt=null;
    PreparedStatement CPstmt=null;
    PreparedStatement cpstmt2=null;
    PreparedStatement pstmt3=null;
    ResultSet CSet2=null;
    ResultSet Lset=null;
    ResultSet CSet=null;
    String card_nums="";
    if(Loperation_Conn!=null && Cloud_Operation_Conn!=null) {
        // Select First Newly Added Contracts
        String Query="select CONTRACT_CARD_NO\n" + 
        ",CONTRACT_PARENT_ID\n" + 
        ", CONTRACT_DATE \n" + 
        ",CONTRACT_SOURCE_ID\n" + 
        ",(CASE WHEN CONTRACT_TYPE='MONTH' THEN 1 when CONTRACT_TYPE='15 DAYS' THEN 2 when CONTRACT_TYPE='MON' THEN 1 ELSE 4 END) as CON_DURATION\n" + 
        ",CONTRACT_CLASSIFICATION_ID\n" + 
        ",CONTRACT_CLIENT_NAME\n" + 
        ",CONTRACT_PHONE\n" + 
        ",CONTRACT_MOBILE\n" + 
        ",CONTRACT_MOBILE2\n" + 
        ",CONTRACT_STATUS\n" + 
        ",CONTRACT_AREA_ID\n" + 
        ", CONTRACT_CANCEL_DATE \n" + 
        ",CONTRACT_CANCEL_REASON\n" + 
        ",CONTRACT_PAYMENT_TYPE_ID\n" + 
        ",CONTRACT_B_COUNT , CONT_NO \n" + 
        "from CONTRACT_INFO@medo  where OPERATION_FLAGE=0 ";
            try {
                String customer_id="";
                String insert_Query2="";
                LPstmt = Loperation_Conn.prepareStatement(Query);
                Lset=LPstmt.executeQuery();
                String insert_Query="";
            insert_Query+="INSERT INTO CUSTOMER_PROFILE(CUSTOMER_ID , CUSTOMER_NAME , CUSTOMER_CHANNEL , CUSTOMER_CLASSIFICATION , CUSTOMER_PHONE , CUSTOMER_MOBILE , COLLECTION_TYPE) VALUES(?,?,?,?,?,?,?)";
           cpstmt2= Cloud_Operation_Conn.prepareStatement(insert_Query);
             
                insert_Query2+="INSERT INTO CONTRACT_INFO (CARD_NO , CUSTOMER_ID , CONTRACT_CLIENT_NAME , REPLACEMENT_DURATION , CONTRACT_PHONE , CONTRACT_MOBILE , CONTRACT_MOBILE2 , CONTRACT_DATE , CANCEL_DATE , CANCEL_REASON , ACTIVE , P_NUM,AREA_ID,CONT_NO) VALUES(?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?)";
                
pstmt3=Cloud_Operation_Conn.prepareStatement(insert_Query2);
                while(Lset.next())
                {
                String Customer_id_seq="Select CUSTOMER_PROFILE_SEQ.nextval as cust_id from DUAL";

                    CPstmt=Cloud_Operation_Conn.prepareStatement(Customer_id_seq);
                    CSet=CPstmt.executeQuery();
                    while(CSet.next()) {
                        customer_id=CSet.getString(CSet.findColumn("cust_id"));
                        System.err.println("Customer_id:"+customer_id);
                    }
                CSet.close();
                    CPstmt.close();
                   
                    cpstmt2.setObject(1,customer_id);
                    cpstmt2.setObject(2,Lset.getString(Lset.findColumn("CONTRACT_CLIENT_NAME")) );
                    cpstmt2.setObject(3, Lset.getString(Lset.findColumn("CONTRACT_SOURCE_ID")));
                cpstmt2.setObject(4, Lset.getString(Lset.findColumn("CONTRACT_CLASSIFICATION_ID")));
                cpstmt2.setObject(5, Lset.getString(Lset.findColumn("CONTRACT_PHONE")));
                cpstmt2.setObject(6, Lset.getString(Lset.findColumn("CONTRACT_MOBILE")));
                cpstmt2.setObject(7, Lset.getString(Lset.findColumn("CONTRACT_PAYMENT_TYPE_ID")));
            cpstmt2.addBatch();
                    pstmt3.setObject(1, Lset.getString(Lset.findColumn("CONTRACT_CARD_NO")));
                pstmt3.setObject(2,customer_id);
                    pstmt3.setObject(3, Lset.getString(Lset.findColumn("CONTRACT_CLIENT_NAME")));
                pstmt3.setObject(4, Lset.getString(Lset.findColumn("CON_DURATION")));
                pstmt3.setObject(5, Lset.getString(Lset.findColumn("CONTRACT_PHONE")));
                pstmt3.setObject(6, Lset.getString(Lset.findColumn("CONTRACT_MOBILE")));
                pstmt3.setObject(7, Lset.getString(Lset.findColumn("CONTRACT_MOBILE2")));
              String Contract_date=Lset.getString(Lset.findColumn("CONTRACT_DATE"));
               String[] x=Contract_date.split(" "); 
                Contract_date=x[0];
                pstmt3.setObject(8,Contract_date);
                String cancel_date=Lset.getString(Lset.findColumn("CONTRACT_CANCEL_DATE"));
                    if (cancel_date !=null) {
                        String [] y=cancel_date.split(" ");
                        cancel_date=y[0];
                    }
                pstmt3.setObject(9, cancel_date);
           

                pstmt3.setObject(10, Lset.getString(Lset.findColumn("CONTRACT_CANCEL_REASON")));
                pstmt3.setObject(11, 1);
                pstmt3.setObject(12, Lset.getInt(Lset.findColumn("CONTRACT_B_COUNT")));
                pstmt3.setObject(13, Lset.getString(Lset.findColumn("CONTRACT_AREA_ID")));
                pstmt3.setObject(14, Lset.getString(Lset.findColumn("CONT_NO")));

pstmt3.addBatch();
                card_nums+=Lset.getInt(Lset.findColumn("CONTRACT_CARD_NO"))+",";
             
            }
           //     System.err.println(cpstmt2);
                if(Cloud_Operation_Conn !=null) {
                
                    cpstmt2.executeBatch();
                    System.err.println("x");

                    pstmt3.executeBatch();
                    System.err.println("x");

                    Cloud_Operation_Conn.commit();
                    if(card_nums.length()>0)
                    {
                        card_nums=card_nums.substring(0, card_nums.length()-1);
                    System.err.println("New Card Num :"+card_nums);
                       // Loperation_Conn=createOldOperationConnection();
                        PreparedStatement stmt_last=null;
                        String update_q="Update CONTRACT_INFO@medo SET CLOUD_FLAGE=1  where OPERATION_FLAGE=0";
                            try {
                                stmt_last = Loperation_Conn.prepareStatement(update_q);
                                stmt_last.executeUpdate();
                                Loperation_Conn.commit();
                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                            }

                        }
                }
                if(pstmt3!=null) {
                    pstmt3.close();
                }
                if(cpstmt2 !=null)
                cpstmt2.close();
                if(Loperation_Conn !=null) {
                    Loperation_Conn.close();
                }
        } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
}
  
    return true;

}
    
    public boolean UploadContractsFromOldOperationFlag() {
        
       Connection Loperation_Conn=createOldOperationConnection();
        PreparedStatement stmt_last=null;
        String update_q="Update CONTRACT_INFO@medo SET CLOUD_FLAGE=1";
            try {
                stmt_last = Loperation_Conn.prepareStatement(update_q);
                stmt_last.executeUpdate();
                Loperation_Conn.commit();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        return true;
    }
 // Upload Updates On Contracts From Local Operation To Operation Cloud With Operation Flag=2 and Cloud Flag is null
public boolean  uploadContractUpdatesFromLocalOperationToCloud() {
   Connection Cloud_Conn=createOperationConnection();
   Connection Local_Conn=createOldOperationConnection();
   PreparedStatement Cloud_stmt=null;
   PreparedStatement Local_stmt=null;
   ResultSet Local_rs=null;
    String Card_num="";

   String Query="select CONTRACT_CARD_NO\n" + 
   ",CONTRACT_PARENT_ID\n" + 
   ",CONTRACT_DATE\n" + 
   ",CONTRACT_SOURCE_ID\n" + 
   ",(CASE WHEN CONTRACT_TYPE='MONTH' THEN 1 when CONTRACT_TYPE='15 DAYS' THEN 2 when CONTRACT_TYPE='MON' THEN 1 ELSE 4 END) as CON_DURATION\n" + 
   ",CONTRACT_CLASSIFICATION_ID\n" + 
   ",CONTRACT_CLIENT_NAME\n" + 
   ",CONTRACT_PHONE\n" + 
   ",CONTRACT_MOBILE\n" + 
   ",CONTRACT_MOBILE2\n" + 
   ",CONTRACT_STATUS\n" + 
   ",CONTRACT_AREA_ID\n" + 
   ",CONTRACT_CANCEL_DATE\n" + 
   ",CONTRACT_CANCEL_REASON\n" + 
   ",CONTRACT_PAYMENT_TYPE_ID\n" + 
   ",CONTRACT_B_COUNT\n" + 
   "from CONTRACT_INFO@medo  WHERE (OPERATION_FLAGE=2) and cloud_flage=0";
        try {
            Local_stmt = Local_Conn.prepareStatement(Query);
            String Update_Query="UPDATE CONTRACT_INFO SET CONTRACT_CLIENT_NAME=? , REPLACEMENT_DURATION =? , CONTRACT_PHONE =? , CONTRACT_MOBILE =? , CONTRACT_MOBILE2 =? , CANCEL_DATE =TO_DATE(?,'YYYY-MM-DD') , CANCEL_REASON =? , P_NUM=? WHERE CARD_NO=?  ";
     Local_rs=Local_stmt.executeQuery();
            Cloud_stmt=Cloud_Conn.prepareStatement(Update_Query);
       while(Local_rs.next()) {
           Cloud_stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("CONTRACT_CLIENT_NAME")));
           Cloud_stmt.setObject(2, Local_rs.getInt(Local_rs.findColumn("CON_DURATION")));
           Cloud_stmt.setObject(3, Local_rs.getString(Local_rs.findColumn("CONTRACT_PHONE")));
           Cloud_stmt.setObject(4, Local_rs.getString(Local_rs.findColumn("CONTRACT_MOBILE")));
           Cloud_stmt.setObject(5, Local_rs.getString(Local_rs.findColumn("CONTRACT_MOBILE2")));
           String cancel_date=Local_rs.getString(Local_rs.findColumn("CONTRACT_CANCEL_DATE"));
           if(cancel_date!=null) {
               String []x=cancel_date.split(" ");
               cancel_date=x[0];
           }
           Cloud_stmt.setObject(6, cancel_date);
           Cloud_stmt.setObject(7, Local_rs.getString(Local_rs.findColumn("CONTRACT_CANCEL_REASON")));
           Cloud_stmt.setObject(8, Local_rs.getInt(Local_rs.findColumn("CONTRACT_B_COUNT")));
           Cloud_stmt.setObject(9, Local_rs.getInt(Local_rs.findColumn("CONTRACT_CARD_NO")));
           Card_num+=Local_rs.getInt(Local_rs.findColumn("CONTRACT_CARD_NO"));
           Cloud_stmt.addBatch();
       }
       Cloud_stmt.executeBatch();
            Cloud_Conn.commit();
            if(Card_num.length()>0)
            {
                Card_num=Card_num.substring(0, Card_num.length()-1);
            System.err.println("New Card Num :"+Card_num);
               // Loperation_Conn=createOldOperationConnection();
                PreparedStatement stmt_last=null;
                String update_q="Update CONTRACT_INFO@medo SET CLOUD_FLAGE=1  WHERE (OPERATION_FLAGE=2)  ";
                    try {
                        stmt_last = Local_Conn.prepareStatement(update_q);
                        stmt_last.executeUpdate();
                        Local_Conn.commit();
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }

                }
            if(Cloud_stmt !=null)
            Cloud_stmt.close();
            if(Local_rs !=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Conn !=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(Card_num.length()>0)
            Card_num=Card_num.substring(0, Card_num.length()-1);
        System.err.println("Updated Cards : "+Card_num);

        return true;
}

// Deleted Contracts On Local Operation to be marked as inactive on cloud operation 
public boolean DeletedContractsFromLocalOperationToCloud() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_stmt=null;
    ResultSet Local_rs=null;
    String Update_Query="";
    String Card_num="";
    String Select_Query="select CONTRACT_CARD_NO\n" + 
    ",CONTRACT_PARENT_ID\n" + 
    ",CONTRACT_DATE\n" + 
    ",CONTRACT_SOURCE_ID\n" + 
    ",(CASE WHEN CONTRACT_TYPE='MONTH' THEN 1 when CONTRACT_TYPE='15 DAYS' THEN 2 when CONTRACT_TYPE='MON' THEN 1 ELSE 4 END) as CON_DURATION\n" + 
    ",CONTRACT_CLASSIFICATION_ID\n" + 
    ",CONTRACT_CLIENT_NAME\n" + 
    ",CONTRACT_PHONE\n" + 
    ",CONTRACT_MOBILE\n" + 
    ",CONTRACT_MOBILE2\n" + 
    ",CONTRACT_STATUS\n" + 
    ",CONTRACT_AREA_ID\n" + 
    ",CONTRACT_CANCEL_DATE\n" + 
    ",CONTRACT_CANCEL_REASON\n" + 
    ",CONTRACT_PAYMENT_TYPE_ID\n" + 
    ",CONTRACT_B_COUNT\n" + 
    "from CONTRACT_INFO@medo  WHERE (OPERATION_FLAGE=3)";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
        Update_Query+="UPDATE CONTRACT_INFO SET ACTIVE=0 WHERE CARD_NO=?";
            Cloud_stmt=Cloud_Conn.prepareStatement(Update_Query);
            while(Local_rs.next()) {
                Card_num+=Local_rs.getInt(Local_rs.findColumn("CONTRACT_CARD_NO"))+",";
                Cloud_stmt.setObject(1, Local_rs.getInt(Local_rs.findColumn("CONTRACT_CARD_NO")));
           Cloud_stmt.addBatch();
            }
            Cloud_stmt.executeBatch();
            Cloud_Conn.commit();
            if(Card_num.length()>0)
            {
                Card_num=Card_num.substring(0, Card_num.length()-1);
            System.err.println("New Card Num :"+Card_num);
               // Loperation_Conn=createOldOperationConnection();
                PreparedStatement stmt_last=null;
                String update_q="Update CONTRACT_INFO@medo SET CLOUD_FLAGE=1 WHERE (OPERATION_FLAGE=3) ";
                    try {
                        stmt_last = Local_Conn.prepareStatement(update_q);
                        stmt_last.executeUpdate();
                        Local_Conn.commit();
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }

                }
            if(Local_rs !=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Cloud_stmt!=null)
            Cloud_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(Card_num.length()>0)
            Card_num=Card_num.substring(0, Card_num.length()-1);
        System.out.println("Cards Marked as Deleted :"+Card_num);
        return true;
}

/*************************************************** Addresses Part ***********************************************************************************************/
public boolean UploadNewContractAddress() {
    Connection Local_conn=createOldOperationConnection();
    Connection Cloud_conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_stmt=null;
    ResultSet Local_rs=null;
    
    String Select_Query="";
    String Insert_Query="";
    String Card_num="";
    
    Select_Query="select ADDRESS_CARD_NO \n" + 
    "    ,ADDRESS_CITY_ID \n" + 
    "    ,ADDRESS_REG_ID \n" + 
    "    ,ADDRESS_AREA_ID \n" + 
    "    ,ADDRESS_STREET_ID \n" + 
    "    ,ADDRESS_HOME_NO \n" + 
    "    ,ADDRESS_FLOR_NO \n" + 
    "    ,ADDRESS_FLAT_NO \n" + 
    "    ,ADDRESS_ADRS_REMARK \n" + 
    "    ,ADDRESS_ACTIVE \n" + 
    "    ,ADDRESS_TEMP \n" + 
    "    ,ADDRESS_DATE \n" + 
    "    ,OPERATION_FLAGE \n" + 
    "    ,CLOUD_FLAGE \n" + 
    "    ,OPERATION_TIME \n" + 
    "    from contract_address@medo where OPERATION_FLAGE IS NULL ";
        try {
            Local_stmt = Local_conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            Insert_Query+="Insert into CONTRACT_ADDRESS(CARD_NO,CITY_ID,REGION_ID,AREA_ID,STREET_ID,ACTIVE,REMARKS , HOME_NO , FLOOR_NO , FLAT_NO,ACTIVE_FROM) VALUES(?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'))";
            Cloud_stmt=Cloud_conn.prepareStatement(Insert_Query);
            while(Local_rs.next()) {
                Cloud_stmt.setObject(1,Local_rs.getString(Local_rs.findColumn("ADDRESS_CARD_NO")));
                Cloud_stmt.setObject(2,Local_rs.getString(Local_rs.findColumn("ADDRESS_CITY_ID")));
                Cloud_stmt.setObject(3,Local_rs.getString(Local_rs.findColumn("ADDRESS_REG_ID")));
                Cloud_stmt.setObject(4,Local_rs.getString(Local_rs.findColumn("ADDRESS_AREA_ID")));
                Cloud_stmt.setObject(5,Local_rs.getString(Local_rs.findColumn("ADDRESS_STREET_ID")));
                Cloud_stmt.setObject(6,1);
                Cloud_stmt.setObject(7,Local_rs.getString(Local_rs.findColumn("ADDRESS_ADRS_REMARK")));
                Cloud_stmt.setObject(8,Local_rs.getString(Local_rs.findColumn("ADDRESS_HOME_NO")));
                Cloud_stmt.setObject(9,Local_rs.getString(Local_rs.findColumn("ADDRESS_FLOR_NO")));
                Cloud_stmt.setObject(10,Local_rs.getString(Local_rs.findColumn("ADDRESS_FLAT_NO")));
             String address_date=Local_rs.getString(Local_rs.findColumn("ADDRESS_DATE"));
                if(address_date!=null) {
                    String [] arr=address_date.split(" ");
                    address_date=arr[0];
                }
                Cloud_stmt.setObject(11,address_date);
System.err.println(address_date);
                Card_num+=Local_rs.getInt(Local_rs.findColumn("ADDRESS_CARD_NO"))+",";
              System.err.println(Local_rs.getInt(Local_rs.findColumn("ADDRESS_CARD_NO")));
                Cloud_stmt.addBatch();
            }
            Cloud_stmt.executeBatch();
            Cloud_conn.commit();
            if(Card_num.length()>0)
            {
                Card_num=Card_num.substring(0,Card_num.length()-1);
            System.err.println("Address Added To Contracts:"+Card_num);
            PreparedStatement stmt_last=null;
            String update_q="Update contract_address@medo SET CLOUD_FLAGE=1 where OPERATION_FLAGE IS NULL ";
            try {
                stmt_last = Local_conn.prepareStatement(update_q);
                stmt_last.executeUpdate();
                Local_conn.commit();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_conn!=null)
            Local_conn.close();
            if(Cloud_stmt!=null)
            Cloud_stmt.close();
            if(Cloud_conn!=null)
            Cloud_conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
       
        return true;
}

public boolean UploadUpdatedContractAddress() {
    Connection Local_conn=createOldOperationConnection();
    Connection cloud_conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String Card_num="";
    Select_Query="select ADDRESS_CARD_NO\n" + 
    ",ADDRESS_CITY_ID\n" + 
    ",ADDRESS_REG_ID\n" + 
    ",ADDRESS_AREA_ID\n" + 
    ",ADDRESS_STREET_ID\n" + 
    ",ADDRESS_HOME_NO\n" + 
    ",ADDRESS_FLOR_NO\n" + 
    ",ADDRESS_FLAT_NO\n" + 
    ",ADDRESS_ADRS_REMARK\n" + 
    ",ADDRESS_ACTIVE\n" + 
    ",ADDRESS_TEMP\n" + 
    ",ADDRESS_DATE\n" + 
    ",OPERATION_FLAGE\n" + 
    ",CLOUD_FLAGE\n" + 
    ",OPERATION_TIME\n" + 
    "from contract_address@medo\n" + 
    " where address_card_no not in (3947)";
        try {
            Local_stmt = Local_conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            Update_Query+="Update CONTRACT_ADDRESS SET CITY_ID=? , REGION_ID=? , AREA_ID=? , STREET_ID=? , REMARKS=? , HOME_NO=? , FLOOR_NO=? , FLAT_NO=? WHERE CARD_NO=?";
            Cloud_stmt=cloud_conn.prepareStatement(Update_Query);
            while(Local_rs.next()) {
                Cloud_stmt.setObject(1,Local_rs.getString(Local_rs.findColumn("ADDRESS_CITY_ID")));
                Cloud_stmt.setObject(2,Local_rs.getString(Local_rs.findColumn("ADDRESS_REG_ID")));
                Cloud_stmt.setObject(3,Local_rs.getString(Local_rs.findColumn("ADDRESS_AREA_ID")));
                Cloud_stmt.setObject(4,Local_rs.getString(Local_rs.findColumn("ADDRESS_STREET_ID")));
                Cloud_stmt.setObject(5,Local_rs.getString(Local_rs.findColumn("ADDRESS_ADRS_REMARK")));
                Cloud_stmt.setObject(6,Local_rs.getString(Local_rs.findColumn("ADDRESS_HOME_NO")));
                Cloud_stmt.setObject(7,Local_rs.getString(Local_rs.findColumn("ADDRESS_FLOR_NO")));
                Cloud_stmt.setObject(8,Local_rs.getString(Local_rs.findColumn("ADDRESS_FLAT_NO")));
                Cloud_stmt.setObject(9,Local_rs.getString(Local_rs.findColumn("ADDRESS_CARD_NO")));
                System.err.println(Local_rs.getString(Local_rs.findColumn("ADDRESS_CARD_NO")));
                Card_num+=Local_rs.getInt(Local_rs.findColumn("ADDRESS_CARD_NO"))+",";
                Cloud_stmt.addBatch();

            }
            Cloud_stmt.executeBatch();
            System.err.println("x");

            cloud_conn.commit();
            System.err.println("x");
            if(Card_num.length()>0)
            {
                Card_num=Card_num.substring(0,Card_num.length()-1);
            System.err.println("Address Added To Contracts:"+Card_num);
            PreparedStatement stmt_last=null;
            String update_q="Update contract_address@medo SET CLOUD_FLAGE=1  ";
            try {
                stmt_last = Local_conn.prepareStatement(update_q);
                stmt_last.executeUpdate();
                Local_conn.commit();
                System.err.println("x");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_conn!=null)
            Local_conn.close();
            if(Cloud_stmt!=null)
            Cloud_stmt.close();
            if(cloud_conn!=null)
            cloud_conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(Card_num.length()>0)
            Card_num=Card_num.substring(0,Card_num.length()-1);
        System.err.println("Updated Contract Address :"+Card_num);
        return true;
}

    public boolean UploadDeletedContractAddress() {
        Connection Local_conn=createOldOperationConnection();
        Connection cloud_conn=createOperationConnection();
        PreparedStatement Local_stmt=null;
        PreparedStatement Cloud_stmt=null;
        ResultSet Local_rs=null;
        String Select_Query="";
        String Update_Query="";
        String Card_num="";
        Select_Query="select ADDRESS_CARD_NO\n" + 
        ",ADDRESS_CITY_ID\n" + 
        ",ADDRESS_REG_ID\n" + 
        ",ADDRESS_AREA_ID\n" + 
        ",ADDRESS_STREET_ID\n" + 
        ",ADDRESS_HOME_NO\n" + 
        ",ADDRESS_FLOR_NO\n" + 
        ",ADDRESS_FLAT_NO\n" + 
        ",ADDRESS_ADRS_REMARK\n" + 
        ",ADDRESS_ACTIVE\n" + 
        ",ADDRESS_TEMP\n" + 
        ",ADDRESS_DATE\n" + 
        ",OPERATION_FLAGE\n" + 
        ",CLOUD_FLAGE\n" + 
        ",OPERATION_TIME\n" + 
        "from contract_address@medo\n" + 
        "where ( OPERATION_FLAGE=3) and cloud_flage =0";
            try {
                Local_stmt = Local_conn.prepareStatement(Select_Query);
                Local_rs=Local_stmt.executeQuery();
                Update_Query+="Update CONTRACT_ADDRESS SET ACTIVE=0 WHERE CARD_NO=?;";
                Cloud_stmt=cloud_conn.prepareStatement(Update_Query);
                while(Local_rs.next()) {
                   Cloud_stmt.setObject(1,Local_rs.getInt(Local_rs.findColumn("ADDRESS_CARD_NO")));
                    Card_num+=Local_rs.getInt(Local_rs.findColumn("ADDRESS_CARD_NO"))+",";
                    Cloud_stmt.addBatch();

                }
                Cloud_stmt.executeBatch();
                cloud_conn.commit();
                if(Local_rs!=null)
                Local_rs.close();
                if(Local_stmt!=null)
                Local_stmt.close();
                if(Local_conn!=null)
                Local_conn.close();
                if(Cloud_stmt!=null)
                Cloud_stmt.close();
                if(cloud_conn!=null)
                cloud_conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            if(Card_num.length()>0)
                Card_num=Card_num.substring(0,Card_num.length()-1);
            System.err.println("Deleted Contract Address :"+Card_num);
            return true;
    }

/****************************************** Product Contract ************************************************************************************************/
    public boolean UploadNewProductContract() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Insert_Query="";
    String Product_Serials="";
    Select_Query="SELECT distinct CON_P.PRODUCT_CARD_NO , CON_P.PRODUCT_ITEM_CODE , CON_P.PRODUCT_QTY , CON_P.PRODUCT_PRICE , CON_P.PRODUCT_PKG_ID,CON_P.PRODUCT_SER_ITEM,CON_P.PRODUCT_AMEND_DATE,CASE WHEN CON_P.PRODUCT_PKG_ID = '0' THEN  CASE WHEN CON_PD.PRODUCT_D_ITEM_TRATMENT IS NULL  THEN '0' ELSE CON_PD.PRODUCT_D_ITEM_TRATMENT END  ELSE '0' END AS TREATMENT_ID \n" + 
    "   ,CON_PD.product_d_new\n" + 
    "    FROM CONTRACT_PRODUCT@medo CON_P \n" + 
    "    LEFT JOIN CONTRACT_PRODUCT_D@medo  CON_PD ON CON_PD.PRODUCT_D_F_ITEM=CON_P.PRODUCT_ITEM_CODE AND CON_PD.PRODUCT_D_CARD_NO=CON_P.PRODUCT_CARD_NO AND CON_P.PRODUCT_PKG_ID='0' and \n" + 
    "    CON_PD.product_d_ser_item=CON_P.product_ser_item\n"; 
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            Insert_Query="INSERT INTO PRODUCT_CONTRACT(CARD_NO , PRODUCT_ID ,PACKAGE_NO , TREATMENT_ID , QUANTITY , UNIT_PRICE, ACTIVE , ACTIVE_FROM , PRODUCT_SERIAL , NEW ) VALUES(?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),? ,?)";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Insert_Query);
            while(Local_rs.next()) {
                Cloud_Stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("PRODUCT_CARD_NO")));
                Cloud_Stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("PRODUCT_ITEM_CODE")));
                Cloud_Stmt.setObject(3, Local_rs.getString(Local_rs.findColumn("PRODUCT_PKG_ID")));
                Cloud_Stmt.setObject(4, Local_rs.getString(Local_rs.findColumn("TREATMENT_ID")));
                Cloud_Stmt.setObject(5, Local_rs.getInt(Local_rs.findColumn("PRODUCT_QTY")));
                Cloud_Stmt.setObject(6, Local_rs.getInt(Local_rs.findColumn("PRODUCT_PRICE")));
                Cloud_Stmt.setObject(7, 1);
                String product_date=Local_rs.getString(Local_rs.findColumn("PRODUCT_AMEND_DATE"));
               if(product_date!=null)
               {
                String [] a=product_date.split(" ");
                product_date=a[0];
               }
                Cloud_Stmt.setObject(8, product_date);
                Cloud_Stmt.setObject(9, Local_rs.getString(Local_rs.findColumn("PRODUCT_SER_ITEM")));
                Cloud_Stmt.setObject(10, Local_rs.getString(Local_rs.findColumn("product_d_new")));

                Product_Serials+=Local_rs.getString(Local_rs.findColumn("PRODUCT_SER_ITEM"))+",";
            Cloud_Stmt.addBatch();
            }
            Cloud_Stmt.executeBatch();
            Cloud_Conn.commit();
            if(Product_Serials.length()>0)
                Product_Serials=Product_Serials.substring(0, Product_Serials.length()-1);
            Local_Conn=createOldOperationConnection();
            PreparedStatement stmt_last=null;
            String update_q="Update CONTRACT_PRODUCT@medo SET CLOUD_FLAGE=1";
            String update_q2=" update contract_product_d@medo SET CLOUD_FLAGE=1 ";
            try {
                stmt_last = Local_Conn.prepareStatement(update_q);
                stmt_last.executeUpdate();
                
                Local_Conn.commit();
                stmt_last = Local_Conn.prepareStatement(update_q2);
                stmt_last.executeUpdate();
                Local_Conn.commit();
            }
            catch (SQLException e) {
               System.err.println(e.getMessage());
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
       

        
        return true;
}

    public boolean UploadUpdatedProductContract() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String Product_Serials="";
    Select_Query="SELECT CON_P.PRODUCT_CARD_NO , CON_P.PRODUCT_ITEM_CODE , CON_P.PRODUCT_QTY , CON_P.PRODUCT_PRICE , CON_P.PRODUCT_PKG_ID,CON_P.PRODUCT_SER_ITEM,CON_P.PRODUCT_AMEND_DATE,CASE WHEN CON_P.PRODUCT_PKG_ID = '0' THEN  CASE WHEN CON_PD.PRODUCT_D_ITEM_TRATMENT IS NULL  THEN '0' ELSE CON_PD.PRODUCT_D_ITEM_TRATMENT END  ELSE '0' END AS TREATMENT_ID\n" + 
    "FROM CONTRACT_PRODUCT@medo CON_P \n" + 
    "LEFT JOIN CONTRACT_PRODUCT_D@medo  CON_PD ON CON_PD.PRODUCT_D_F_ITEM=CON_P.PRODUCT_ITEM_CODE AND CON_PD.PRODUCT_D_CARD_NO=CON_P.PRODUCT_CARD_NO AND CON_P.PRODUCT_PKG_ID='0'\n" + 
    "WHERE (CON_PD.operation_flage=2) and CON_PD.cloud_flage=0";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            Update_Query="UPDATE PRODUCT_CONTRACT SET PRODUCT_ID=? , PACKAGE_NO=? ,TREATMENT_ID=? , QUANTITY=? , UNIT_PRICE=? WHERE PRODUCT_SERIAL=? ;";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Update_Query);
            while(Local_rs.next()) {
                Cloud_Stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("PRODUCT_ITEM_CODE")));
                Cloud_Stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("PRODUCT_PKG_ID")));
                Cloud_Stmt.setObject(3, Local_rs.getString(Local_rs.findColumn("TREATMENT_ID")));
                Cloud_Stmt.setObject(4, Local_rs.getInt(Local_rs.findColumn("PRODUCT_QTY")));
                Cloud_Stmt.setObject(5, Local_rs.getInt(Local_rs.findColumn("PRODUCT_PRICE")));
                Cloud_Stmt.setObject(6, Local_rs.getString(Local_rs.findColumn("PRODUCT_SER_ITEM")));

                Product_Serials+=Local_rs.getString(Local_rs.findColumn("PRODUCT_SER_ITEM"))+",";
            Cloud_Stmt.addBatch();
            }
            Cloud_Stmt.executeBatch();
            Cloud_Conn.commit();
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(Product_Serials.length()>0)
            Product_Serials=Product_Serials.substring(0, Product_Serials.length()-1);
    System.out.println("Updated Product_Contract :"+Product_Serials);
        return true;
    }

    public boolean UploadDeletedProductContract() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String Product_Serials="";
    Select_Query="SELECT CON_P.PRODUCT_CARD_NO , CON_P.PRODUCT_ITEM_CODE , CON_P.PRODUCT_QTY , CON_P.PRODUCT_PRICE , CON_P.PRODUCT_PKG_ID,CON_P.PRODUCT_SER_ITEM,CON_P.PRODUCT_AMEND_DATE,CASE WHEN CON_P.PRODUCT_PKG_ID = '0' THEN  CASE WHEN CON_PD.PRODUCT_D_ITEM_TRATMENT IS NULL  THEN '0' ELSE CON_PD.PRODUCT_D_ITEM_TRATMENT END  ELSE '0' END AS TREATMENT_ID\n" + 
    "FROM CONTRACT_PRODUCT@medo CON_P \n" + 
    "LEFT JOIN CONTRACT_PRODUCT_D@medo  CON_PD ON CON_PD.PRODUCT_D_F_ITEM=CON_P.PRODUCT_ITEM_CODE AND CON_PD.PRODUCT_D_CARD_NO=CON_P.PRODUCT_CARD_NO AND CON_P.PRODUCT_PKG_ID='0'\n" + 
    "WHERE (CON_PD.operation_flage=3) and CON_PD.cloud_flage =0";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            Update_Query="UPDATE PRODUCT_CONTRACT SET ACTIVE=0 WHERE PRODUCT_SERIAL=? ;";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Update_Query);
            while(Local_rs.next()) {
                Cloud_Stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("PRODUCT_SER_ITEM")));

                Product_Serials+=Local_rs.getString(Local_rs.findColumn("PRODUCT_SER_ITEM"))+",";
            Cloud_Stmt.addBatch();
            }
            Cloud_Stmt.executeBatch();
            Cloud_Conn.commit();
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(Product_Serials.length()>0)
            Product_Serials=Product_Serials.substring(0, Product_Serials.length()-1);
    System.out.println("Deleted Product_Contract :"+Product_Serials);
        return true;
    }
    
    
    
    public boolean UploadCity() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String InsertedCity="";
    String UpdatedCity="";
    String InsertQuery="";
    PreparedStatement Insert_stmt=null;
    Select_Query="select CITY_CODE , \n" + 
    "    CITY_NAME ,  \n" + 
    "    OPERATION_FLAGE  , to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss') from ADDRESS_CITY@medo /*where CLOUD_FLAGE=0 AND OPERATION_FLAGE in (1,2) and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1*/";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            InsertQuery="INSERT INTO CITY(CITY_ID , CITY_NAME) VALUES(?,?)";
            Update_Query="UPDATE CITY SET CITY_NAME=? WHERE CITY_ID=? ";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Update_Query);
            Insert_stmt=Cloud_Conn.prepareStatement(InsertQuery);
            while(Local_rs.next()) {
               /* if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==2) {
                    Cloud_Stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("CITY_NAME")));
                    Cloud_Stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("CITY_CODE")));
                    Cloud_Stmt.addBatch();
                    UpdatedCity+=Local_rs.getInt(Local_rs.findColumn("CITY_CODE"))+",";

                }
                else if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==null){*/
                    Insert_stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("CITY_CODE")));
                    Insert_stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("CITY_NAME")));
                    Insert_stmt.addBatch();
                    InsertedCity+=Local_rs.getInt(Local_rs.findColumn("CITY_CODE"))+",";
 
                /*}
                else {
                    
                }
*/
            }
            Insert_stmt.executeBatch();
            Cloud_Stmt.executeBatch();
            
            Cloud_Conn.commit();
            if(InsertedCity.length()>0 || UpdatedCity.length()>0) {
                String Update_local="Update ADDRESS_CITY@medo set Cloud_flage=1 /*where CLOUD_FLAGE=0 AND OPERATION_FLAGE in (1,2)  and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1*/";
                Local_stmt=Local_Conn.prepareStatement(Update_local);
                Local_stmt.executeUpdate();
                Local_Conn.commit();
                System.err.println("InsertedCity:"+InsertedCity);
                System.err.println("UpdatedCity:"+UpdatedCity);
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
          return true;
    }
    
    
    
    public boolean UploadRegion() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String InsertedData="";
    String UpdatedData="";
    String InsertQuery="";
    PreparedStatement Insert_stmt=null;
    Select_Query="select REG_CODE , \n" + 
    "    REG_NAME , CITY_CODE , \n" + 
    "    OPERATION_FLAGE  , to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss') from ADDRESS_REG@medo /*where CLOUD_FLAGE=0 AND  OPERATION_FLAGE in (1,2) and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1*/";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            InsertQuery="INSERT INTO REGION(REGION_ID , REGION_NAME , CITY_ID) VALUES(?,?,?)";
            Update_Query="UPDATE CITY SET REGION_NAME=? WHERE REGION_ID=? AND CITY_ID=? ";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Update_Query);
            Insert_stmt=Cloud_Conn.prepareStatement(InsertQuery);
            while(Local_rs.next()) {
               /* if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==2) {
                    Cloud_Stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("REG_NAME")));
                    Cloud_Stmt.setObject(2, Local_rs.getInt(Local_rs.findColumn("REG_CODE")));
                    Cloud_Stmt.setObject(3, Local_rs.getInt(Local_rs.findColumn("CITY_CODE")));

                    Cloud_Stmt.addBatch();
                    UpdatedData+=Local_rs.getInt(Local_rs.findColumn("REG_CODE"))+",";

                }
                else if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==1){
                 */   Insert_stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("REG_CODE")));
                    Insert_stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("REG_NAME")));
                    Insert_stmt.setObject(3, Local_rs.getString(Local_rs.findColumn("CITY_CODE")));

                    Insert_stmt.addBatch();
                    InsertedData+=Local_rs.getInt(Local_rs.findColumn("REG_CODE"))+",";
    
                /*}
                else {
                    
                }
*/
            }
            Insert_stmt.executeBatch();
            Cloud_Stmt.executeBatch();
            
            Cloud_Conn.commit();
            if(InsertedData.length()>0 || UpdatedData.length()>0) {
                String Update_local="Update ADDRESS_REG@medo set Cloud_flage=1 /*where CLOUD_FLAGE=0 AND OPERATION_FLAGE in (1,2) and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1*/";
                Local_stmt=Local_Conn.prepareStatement(Update_local);
                Local_stmt.executeUpdate();
                Local_Conn.commit();
                System.err.println("InsertedRegion:"+InsertedData);
                System.err.println("UpdatedRegion:"+UpdatedData);
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
          return true;
    }
    
    
    
    public boolean UploadArea() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String InsertedData="";
    String UpdatedData="";
    String InsertQuery="";
    PreparedStatement Insert_stmt=null;
    Select_Query="select AREA_CODE , \n" + 
    "    AREA_NAME , CITY_CODE , REG_CODE ,  \n" + 
    "    OPERATION_FLAGE  , to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss') from ADDRESS_AREA@medo /*where CLOUD_FLAGE=0 AND  OPERATION_FLAGE in (1,2) and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1*/";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            InsertQuery="INSERT INTO AREA(AREA_ID , AREA_NAME , CITY_ID,REGION_ID) VALUES(?,?,?,?)";
            Update_Query="UPDATE AREA SET AREA_NAME=? WHERE AREA_ID=? AND REGION_ID=? AND CITY_ID=? ";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Update_Query);
            Insert_stmt=Cloud_Conn.prepareStatement(InsertQuery);
            while(Local_rs.next()) {
                /*if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==2) {
                    Cloud_Stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("AREA_NAME")));
                    Cloud_Stmt.setObject(2, Local_rs.getInt(Local_rs.findColumn("AREA_CODE")));
                    Cloud_Stmt.setObject(3, Local_rs.getInt(Local_rs.findColumn("REG_CODE")));
                    Cloud_Stmt.setObject(4, Local_rs.getInt(Local_rs.findColumn("CITY_CODE")));

                    Cloud_Stmt.addBatch();
                    UpdatedData+=Local_rs.getInt(Local_rs.findColumn("AREA_CODE"))+",";

                }
                else if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==1){
                  */  Insert_stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("AREA_CODE")));
                    Insert_stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("AREA_NAME")));
                    Insert_stmt.setObject(3, Local_rs.getString(Local_rs.findColumn("CITY_CODE")));
                    Insert_stmt.setObject(4, Local_rs.getString(Local_rs.findColumn("REG_CODE")));

                    Insert_stmt.addBatch();
                    InsertedData+=Local_rs.getInt(Local_rs.findColumn("AREA_CODE"))+",";
    
                /*}
                else {
                    
                }
*/
            }
            Insert_stmt.executeBatch();
            Cloud_Stmt.executeBatch();
            
            Cloud_Conn.commit();
            if(InsertedData.length()>0 || UpdatedData.length()>0) {
                String Update_local="Update ADDRESS_AREA@medo set Cloud_flage=1 /* where CLOUD_FLAGE=0 AND  OPERATION_FLAGE in (1,2)  and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1 */ ";
                Local_stmt=Local_Conn.prepareStatement(Update_local);
                Local_stmt.executeUpdate();
                Local_Conn.commit();
                System.err.println("InsertedRegion:"+InsertedData);
                System.err.println("UpdatedRegion:"+UpdatedData);
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
          return true;
    }
    
    
    
    
    public boolean UploadStreet() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String InsertedData="";
    String UpdatedData="";
    String InsertQuery="";
    PreparedStatement Insert_stmt=null;
    Select_Query="select STREET_CODE , \n" + 
    "    STREET_NAME , CITY_CODE , REG_CODE , AREA_CODE  , \n" + 
    "    OPERATION_FLAGE  , to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss') from ADDRESS_STREET@medo /* where CLOUD_FLAGE=0 AND OPERATION_FLAGE =1  and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1 */ ";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            InsertQuery="INSERT INTO STREET(STREET_ID , STREET_NAME , CITY_ID,REGION_ID,AREA_ID) VALUES(?,?,?,?,?)";
            Update_Query="UPDATE STREET SET STREET_NAME=? WHERE STREET_ID=? AND AREA_ID=? AND REGION_ID=? AND CITY_ID=? ";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Update_Query);
            Insert_stmt=Cloud_Conn.prepareStatement(InsertQuery);
            while(Local_rs.next()) {
                System.err.println("Street :"+Local_rs.getString(Local_rs.findColumn("STREET_CODE")));
               /* if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==2) {
                    Cloud_Stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("STREET_NAME")));
                    Cloud_Stmt.setObject(2, Local_rs.getInt(Local_rs.findColumn("STREET_CODE")));
                    Cloud_Stmt.setObject(3, Local_rs.getInt(Local_rs.findColumn("AREA_CODE")));
                    Cloud_Stmt.setObject(4, Local_rs.getInt(Local_rs.findColumn("REG_CODE")));
                    Cloud_Stmt.setObject(5, Local_rs.getInt(Local_rs.findColumn("CITY_CODE")));

                    Cloud_Stmt.addBatch();
                    UpdatedData+=Local_rs.getInt(Local_rs.findColumn("STREET_CODE"))+",";

                }
                else if(Local_rs.getInt(Local_rs.findColumn("OPERATION_FLAGE"))==1){
                 */   Insert_stmt.setObject(1, Local_rs.getString(Local_rs.findColumn("STREET_CODE")));
                    Insert_stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("STREET_NAME")));
                    Insert_stmt.setObject(3, Local_rs.getString(Local_rs.findColumn("CITY_CODE")));
                    Insert_stmt.setObject(4, Local_rs.getString(Local_rs.findColumn("REG_CODE")));
                    Insert_stmt.setObject(5, Local_rs.getString(Local_rs.findColumn("AREA_CODE")));

                    Insert_stmt.addBatch();
                    InsertedData+=Local_rs.getInt(Local_rs.findColumn("STREET_CODE"))+",";
    
                /*}
                else {
                    
                }
*/
            }
            Insert_stmt.executeBatch();
            Cloud_Stmt.executeBatch();
            
            Cloud_Conn.commit();
            if(InsertedData.length()>0 || UpdatedData.length()>0) {
                String Update_local="Update ADDRESS_STREET@medo set Cloud_flage=1 /* where CLOUD_FLAGE=0 AND OPERATION_FLAGE in (1,2) and to_date(OPERATION_TIME,'dd-MON-yyyy HH:mi:ss')=current_date-1 */";
                Local_stmt=Local_Conn.prepareStatement(Update_local);
                Local_stmt.executeUpdate();
                Local_Conn.commit();
                System.err.println("InsertedRegion:"+InsertedData);
                System.err.println("UpdatedRegion:"+UpdatedData);
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
          return true;
    }
    
    

    
    
    
    
    
    public boolean FlushTablesAfterUpload() {
        Connection Op_connection=createOldOperationConnection();
        CallableStatement statement;

        try {
            statement = Op_connection.prepareCall(" { ?= call FLUSH_TABLES_AFTER_UPLOAD@medo() } ");
            statement.registerOutParameter(1,Types.VARCHAR);
            statement.execute();
            Op_connection.commit();
                statement.close();
            Op_connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
      
           
    return true;
    
    }
    
    
    
    
    public boolean UploadDeliveryDates() {
    Connection Local_Conn=createOldOperationConnection();
    Connection Cloud_Conn=createOperationConnection();
    PreparedStatement Local_stmt=null;
    PreparedStatement Cloud_Stmt=null;
    ResultSet Local_rs=null;
    String Select_Query="";
    String Update_Query="";
    String InsertedCity="";
    String UpdatedCity="";
    String InsertQuery="";
    PreparedStatement Insert_stmt=null;
    Select_Query="select distinct delivery_dates_card_no  , delivery_dates_change_date\n" + 
    "    from contract_delivery_dates@medo  ";
        try {
            Local_stmt = Local_Conn.prepareStatement(Select_Query);
            Local_rs=Local_stmt.executeQuery();
            Update_Query="UPDATE CONTRACT_INFO SET NEXT_DELIVERY_DATE=TO_DATE(?,'YYYY-MM-DD') WHERE CARD_NO=? ";
            Cloud_Stmt=Cloud_Conn.prepareStatement(Update_Query);
            while(Local_rs.next()) {
                System.err.println(Local_rs.getString(Local_rs.findColumn("delivery_dates_change_date")));
                  String date_x=Local_rs.getString(Local_rs.findColumn("delivery_dates_change_date"));
                if(date_x!=null) {
                              String [] a=date_x.split(" ");
                              date_x=a[0];
                             }
                    Cloud_Stmt.setObject(1, date_x);
                    Cloud_Stmt.setObject(2, Local_rs.getString(Local_rs.findColumn("delivery_dates_card_no")));
                    Cloud_Stmt.addBatch();
                    UpdatedCity+=Local_rs.getInt(Local_rs.findColumn("delivery_dates_card_no"))+",";

            }
            Cloud_Stmt.executeBatch();
            
            Cloud_Conn.commit();
            if(InsertedCity.length()>0 || UpdatedCity.length()>0) {
                String Update_local="Update contract_delivery_dates@medo set cloud_flage=1";
                Local_stmt=Local_Conn.prepareStatement(Update_local);
                Local_stmt.executeUpdate();
                Local_Conn.commit();
                System.err.println("UpdatedContracts:"+UpdatedCity);
            }
            if(Local_rs!=null)
            Local_rs.close();
            if(Local_stmt!=null)
            Local_stmt.close();
            if(Local_Conn!=null)
            Local_Conn.close();
            if(Cloud_Stmt!=null)
            Cloud_Stmt.close();
            if(Cloud_Conn!=null)
            Cloud_Conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
          return true;
    }
    
    
    /******************************************************************** Download Last Version Of Assignments From DMS **************************************************************************************************************************************************/
    public boolean DownloadDailyAssignments(String Assign_date) {
        Connection DMS_Conn=createDMSConnection();
        Connection OP_Conn=createOperationConnection();
        ResultSet DMS_rs=null;
        ResultSet DMS_Prs=null;
        String Ass_ID="";
        PreparedStatement DMS_pstmt=null;
        PreparedStatement DMS_ppstmt=null;
        PreparedStatement Op_pstmt=null;
        String DMS_Select_Q="";
        String DMS_PSelect_Q="";
        String Op_Ass_Str="";
        String Op_Prod_Str="";
        if(DMS_Conn!=null && OP_Conn!=null) {
            DMS_Select_Q="SELECT \"AREA_NAME\" ,\"CLIENT_NAME\",\"ALLOW_CREDIT\", \"ASSIGN_DATE\",\"STATUS\",\"ID\" ,COALESCE(\"CONTRACT_STATUS\",1) AS \"CONTRACT_STATUS\" ,COALESCE(\"ASSIGNMENTS_WORK_XY\".\"CLOSE_CODE\",1) AS \"CLOSE_CODE\", \n" + 
            "              COALESCE(\"ASSIGNMENTS_WORK_XY\".\"CLOSE_CODE_REASON\",'Not Processed') AS \"CLOSE_CODE_REASON\" , \"GEOCODE_X\" , \"GEOCODE_Y\" ,  \n" + 
            "                                  COALESCE( \"CUSTOMER_NOTE\", \"CUSTOMER_NOTES\") as \"CUSTOMER_NOTE\" , COALESCE(to_char(\"ACTION_DATE\" ,'DD-MON-YY HH24:mi:SS'),to_char(\"ASSIGN_DATE\" ,'DD-MON-YY HH24:mi:SS')) as\"ACTION_DATE\", \"RECIEPT_NO\" ,  \n" + 
            "                                      \"TOTAL_PRICE_PAID\" ,\"ASSIGNMENTS_WORK_XY\" . \"CARD_NO\" ,   \n" + 
            "                                      \"PILOT_ID\" ,\"DRIV_ID\" ,\"ASSIGN_ID\" ,\"ASSIGNMENTS_WORK_XY\" .\"AID\" , \n" + 
            "                                     \"CLOSE_CODE_CANCEL\" ,\"CANCEL_CLOSE_CODE_REASON\",\"ASSIGNMENT_TYPE\", \n" + 
            "                                       \"SPARE_ID\",\"Duration\",\"ACTUAL_PAID\",\"CUSTOMER_TYPE\" ,\"PRIORITY\"  ,\"ASSIGNMENTS_WORK_XY\".\"AREA_ID\" , right(\"ASSIGNMENTS_WORK_XY\".\"FU_Note\",4000) AS FU_Note  , /*LEFT(\"ASSIGNMENTS_WORK_XY\".\"TICKETING_NOTES\" ,3000)*/ '' AS TICKETING_NOTES ,\"ASSIGNMENTS_WORK_XY\".\"PERFORMER\"  \n" + 
            "                                        FROM \"ASSIGNMENTS_WORK_XY\"   \n" + 
            "                                               LEFT JOIN \"ASSIGNED_CONTRACT_D\" ON \"ASSIGNMENTS_WORK_XY\".\"AID\" = \"ASSIGNED_CONTRACT_D\".\"AID\"  \n" + 
            "                                          where \"ASSIGN_DATE\" ::date='"+Assign_date+"'  \n" + 
            "                                           AND (\"ID\" IS NULL OR  \"ID\" IN (SELECT MAX(\"ID\") FROM \"ASSIGNED_CONTRACT_D\" ,\"ASSIGNMENTS_WORK_XY\"   \n" + 
            "                                                        WHERE \"ACTION_DATE\"::DATE='"+Assign_date+"' AND \"ASSIGNED_CONTRACT_D\".\"AID\" = \"ASSIGNMENTS_WORK_XY\".\"AID\"   \n" + 
            "                                               AND \"ASSIGNMENTS_WORK_XY\".\"ASSIGNMENT_TYPE\" != 4 GROUP BY \"ASSIGNED_CONTRACT_D\".\"CARD_NO\")) \n" + 
           // "                                    --  and (\"Oracle_flag\" !=1 or \"Oracle_flag\"  isnull)  \n" + 
            "                                            and\"ASSIGNMENTS_WORK_XY\".\"CHANGED_STATE\" IN (0,1) and \"ASSIGNMENTS_WORK_XY\".\"ASSIGNMENT_TYPE\" !=4 \n" + 
            "           \n" + 
            "\n";
            try {
                DMS_pstmt = DMS_Conn.prepareStatement(DMS_Select_Q);
                if(DMS_pstmt!=null) {
                    DMS_rs=DMS_pstmt.executeQuery();
                    Op_Ass_Str+="INSERT INTO ASSIGNMMENT_DOWNLOAD(ASSIGN_ID,CLOUD_ID,CONTRACT_STATUS,CLOSE_CODE,CLOSE_CODE_REASON,GEOCODE_X,GEOCODE_Y,CUSTOMER_NOTES,ACTION_DATE,RECIEPT_NO,TOTAL_PRICE_PAID,CARD_NO,PILOT_ID,DRIV_ID,CLOSE_CODE_CANCEL,CANCEL_CLOSE_CODE_REASON,ASSIGNMENT_TYPE,SPARE_ID,DURATION,ACTUAL_PAID,ASSIGNMENT_ID,CUSTOMER_TYPE,AREA_ID,INSERT_TIME,PRIORITY,FU_NOTE,TICKETING_NOTES,PERFORMER,ASSIGN_DATE,AREA_NAME,STATUS,CLIENT_NAME , ALLOW_CREDIT)\n" + 
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?)";
                    Op_pstmt=OP_Conn.prepareStatement(Op_Ass_Str);
                    if(DMS_rs!=null) {
                        while(DMS_rs.next()) {
                            System.err.println("Before Start");
                            Op_pstmt.setObject(1, DMS_rs.getString(DMS_rs.findColumn("ASSIGN_ID")));
                            System.err.println("id");
                            
                            
                            Op_pstmt.setObject(2, DMS_rs.getInt(DMS_rs.findColumn("ID")));
                            Op_pstmt.setObject(3, DMS_rs.getInt(DMS_rs.findColumn("CONTRACT_STATUS")));
                            Op_pstmt.setObject(4, DMS_rs.getInt(DMS_rs.findColumn("CLOSE_CODE")));

                            Op_pstmt.setObject(5, DMS_rs.getString(DMS_rs.findColumn("CLOSE_CODE_REASON")));
                            Op_pstmt.setObject(6, DMS_rs.getString(DMS_rs.findColumn("GEOCODE_X")));
                            Op_pstmt.setObject(7, DMS_rs.getString(DMS_rs.findColumn("GEOCODE_Y")));
                            Op_pstmt.setObject(8, DMS_rs.getString(DMS_rs.findColumn("CUSTOMER_NOTE")));
                            System.err.println("Action Date"+ DMS_rs.getString(DMS_rs.findColumn("ACTION_DATE")));
                            Op_pstmt.setObject(9, DMS_rs.getString(DMS_rs.findColumn("ACTION_DATE")));
                            Op_pstmt.setObject(10, DMS_rs.getString(DMS_rs.findColumn("RECIEPT_NO")));
                            Op_pstmt.setObject(11, DMS_rs.getInt(DMS_rs.findColumn("TOTAL_PRICE_PAID")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("TOTAL_PRICE_PAID")));

                            Op_pstmt.setObject(12, DMS_rs.getInt(DMS_rs.findColumn("CARD_NO")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("CARD_NO")));

                            Op_pstmt.setObject(13, DMS_rs.getString(DMS_rs.findColumn("PILOT_ID")));
                            Op_pstmt.setObject(14, DMS_rs.getString(DMS_rs.findColumn("DRIV_ID")));
                            Op_pstmt.setObject(15, DMS_rs.getInt(DMS_rs.findColumn("CLOSE_CODE_CANCEL")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("CLOSE_CODE_CANCEL")));

                            Op_pstmt.setObject(16, DMS_rs.getString(DMS_rs.findColumn("CANCEL_CLOSE_CODE_REASON")));
                            Op_pstmt.setObject(17, DMS_rs.getInt(DMS_rs.findColumn("ASSIGNMENT_TYPE")));
                            Op_pstmt.setObject(18, DMS_rs.getString(DMS_rs.findColumn("SPARE_ID")));
                            Op_pstmt.setObject(19, DMS_rs.getInt(DMS_rs.findColumn("Duration")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("Duration")));

                            Op_pstmt.setObject(20, DMS_rs.getInt(DMS_rs.findColumn("ACTUAL_PAID")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("ACTUAL_PAID")));

                            Op_pstmt.setObject(21, DMS_rs.getInt(DMS_rs.findColumn("AID")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("AID")));
                            System.err.println("Customer type :"+DMS_rs.getString(DMS_rs.findColumn("CUSTOMER_TYPE")));
                            Op_pstmt.setObject(22, DMS_rs.getString(DMS_rs.findColumn("CUSTOMER_TYPE")));
                            Op_pstmt.setObject(23, DMS_rs.getInt(DMS_rs.findColumn("AREA_ID")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("AREA_ID")));

                            Op_pstmt.setObject(24, DMS_rs.getInt(DMS_rs.findColumn("PRIORITY")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("PRIORITY")));

                            Op_pstmt.setObject(25, DMS_rs.getString(DMS_rs.findColumn("FU_Note")));
                            Op_pstmt.setObject(26, DMS_rs.getString(DMS_rs.findColumn("TICKETING_NOTES")));
                            Op_pstmt.setObject(27, DMS_rs.getInt(DMS_rs.findColumn("PERFORMER")));
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("PERFORMER")));
                            System.err.println(DMS_rs.getString(DMS_rs.findColumn("ASSIGN_DATE")));

                            Op_pstmt.setObject(28, DMS_rs.getString(DMS_rs.findColumn("ASSIGN_DATE")));
                            Op_pstmt.setObject(29, DMS_rs.getString(DMS_rs.findColumn("AREA_NAME")));
                            Op_pstmt.setObject(30, DMS_rs.getString(DMS_rs.findColumn("STATUS")));
                            Op_pstmt.setObject(31, DMS_rs.getString(DMS_rs.findColumn("CLIENT_NAME")));
                            Op_pstmt.setObject(32, DMS_rs.getString(DMS_rs.findColumn("ALLOW_CREDIT")));

                            System.err.println("Before Append");
                            System.err.println(DMS_rs.getInt(DMS_rs.findColumn("AID")));
                            Ass_ID+=DMS_rs.getInt(DMS_rs.findColumn("AID"))+",";
                            Op_pstmt.addBatch();
                            System.err.println("After Append");

                        }
                        System.err.println("Execute");
                        Op_pstmt.executeBatch();
                        OP_Conn.commit();
                        if(Ass_ID.length()>0) {
                            Ass_ID=Ass_ID.substring(0, Ass_ID.length()-1);
                            DMS_PSelect_Q="SELECT \"ASSIGNMENTS_WORK_XY\".\"AID\" ,MIN(\"PRODUCT_ID\") AS \"HANDLE_CODE\", MIN(\"PRODUCT_CONTRACT\".\"AID\") AS \"AID\",\n" + 
                  "                  MIN(\"ITEM_ID\") AS \"ITEM_ID\", \"COMPONENT_ID\" AS \"COMPONENT_ID\", \n" + 
                  "                  MIN(\"QUNTITY\") AS \"QUNTITY\", MIN(\"QUNTITY_REPLACED\") AS \"QUNTITY_REPLACED\",  \n" + 
                  "                                              MIN(\"UNIT_PRICE\") AS \"UNIT_PRICE\",     \n" + 
                  "                                               MIN(\"CARD NO\") AS \"CARD NO\", MIN(\"BUY_PRICE\") AS \"BUY_PRICE\" \n" + 
                  "                                               , MIN(\"PRODUCT_CONTRACT\".\"ASSIGN_ID\") AS \"ASSIGN_ID\", \"Treatment code\",\"Package_NO\", \n" + 
                  "                                              MIN(\"DRIV_ID\") AS\"DRIV_ID\" ,MAX(to_char(\"ACTION_DATE\",'DD-MON-YY HH24:mi:SS') )as ACTION_DATE  \n" + 
                  "                                           ,MIN(\"Quantity_cancel\") AS \"Quantity_cancel\" ,MIN(\"Quantity_Dirty\") \n" + 
                  "                                           AS \"Quantity_Dirty\",min(\"UNIT_PRICE\") as UNIT_PRICE \n" + 
                  "                                           ,MIN(\"PRODUCT_CONTRACT\".\"HANDLE\") AS \"HANDLE\",\"DELIVERY_TYPE\"  \n" + 
                  "                                               	FROM \"PRODUCT_CONTRACT\"\n" + 
                  "                                                LEFT JOIN \"ASSIGNMENTS_WORK_XY\" ON \"ASSIGNMENTS_WORK_XY\".\"AID\"=\"PRODUCT_CONTRACT\".\"AID\"\n" + 
                  "                                                LEFT JOIN \"ASSIGNED_CONTRACT_D\" ON \"ASSIGNED_CONTRACT_D\".\"AID\"=\"PRODUCT_CONTRACT\".\"AID\"\n" + 
                  "                                                LEFT JOIN \"PRODUCT\" ON \"PRODUCT_HANDLE\"=\"COMPONENT_ID\"\n" + 
                  "                                                 WHERE \"PRODUCT_CONTRACT\".\"AID\" IN("+Ass_ID+")\n" + 
              //    "                                   --     and (\"PRODUCT_CONTRACT\".\"Oracle_flag\"!=1 or \"PRODUCT_CONTRACT\".\"Oracle_flag\" isnull)  \n" + 
                  "                                             and  (\"PRODUCT_CONTRACT\".\"HANDLE\"  IS NULL OR \"PRODUCT_CONTRACT\".\"HANDLE\"=0)\n" + 
                  "                                                 GROUP BY \"COMPONENT_ID\", \"ITEM_ID\",\"Treatment code\",\"Package_NO\",\"DELIVERY_TYPE\", \n" + 
                  "                                                      \"PRODUCT_CONTRACT\".\"ASSIGN_ID\",\"ASSIGNMENTS_WORK_XY\".\"CARD_NO\",\"ASSIGNMENTS_WORK_XY\".\"AID\" \n" + 
                  "                                                 ORDER BY \"ASSIGNMENTS_WORK_XY\".\"CARD_NO\",\"PRODUCT_CONTRACT\".\"ASSIGN_ID\" " ;
                
                            Op_pstmt=null;
                            Op_Prod_Str+="INSERT INTO PRODUCT_CONTRACT_D_DOWNLOAD(ASSIGN_ID,ITEM_ID,COMPONENT_ID,QUNTITY,QUNTITY_REPLACED,TREATMENT_CODE,PACKAGE_NO,CARD_NO,QUANTITY_CANCEL,QUANTITY_DIRTY,HANDLE,DELIVERY_TYPE,AID,HANDLE_CODE,UNIT_PRICE,ASSIGNMENT_ID)\n" + 
                            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";   
                            Op_pstmt=OP_Conn.prepareStatement(Op_Prod_Str);
                            DMS_ppstmt=DMS_Conn.prepareStatement(DMS_PSelect_Q);
                            DMS_Prs=DMS_ppstmt.executeQuery();
                            while(DMS_Prs.next()) {
                                Op_pstmt.setObject(1, DMS_Prs.getString(DMS_Prs.findColumn("ASSIGN_ID")));
                                Op_pstmt.setObject(2, DMS_Prs.getString(DMS_Prs.findColumn("ITEM_ID")));
                                Op_pstmt.setObject(3, DMS_Prs.getString(DMS_Prs.findColumn("COMPONENT_ID")));
                                Op_pstmt.setObject(4, DMS_Prs.getInt(DMS_Prs.findColumn("QUNTITY")));
                                Op_pstmt.setObject(5, DMS_Prs.getInt(DMS_Prs.findColumn("QUNTITY_REPLACED")));
                                Op_pstmt.setObject(6, DMS_Prs.getInt(DMS_Prs.findColumn("Treatment code")));
                                Op_pstmt.setObject(7, DMS_Prs.getInt(DMS_Prs.findColumn("Package_NO")));
                                Op_pstmt.setObject(8, DMS_Prs.getInt(DMS_Prs.findColumn("CARD NO")));
                                Op_pstmt.setObject(9, DMS_Prs.getInt(DMS_Prs.findColumn("Quantity_cancel")));
                                Op_pstmt.setObject(10, DMS_Prs.getInt(DMS_Prs.findColumn("Quantity_Dirty")));
                                Op_pstmt.setObject(11, DMS_Prs.getInt(DMS_Prs.findColumn("HANDLE")));
                                Op_pstmt.setObject(12, DMS_Prs.getString(DMS_Prs.findColumn("DELIVERY_TYPE")));
                                Op_pstmt.setObject(13, DMS_Prs.getInt(DMS_Prs.findColumn("AID")));
                                Op_pstmt.setObject(14, DMS_Prs.getString(DMS_Prs.findColumn("HANDLE_CODE")));
                                Op_pstmt.setObject(15, DMS_Prs.getInt(DMS_Prs.findColumn("UNIT_PRICE")));
                                Op_pstmt.setObject(16, DMS_Prs.getInt(DMS_Prs.findColumn("AID")));

                                Op_pstmt.addBatch();
                            }
                            Op_pstmt.executeBatch();
                        OP_Conn.commit();
                            DMS_PSelect_Q="SELECT \"ASSIGNMENTS_WORK_XY\".\"AID\" ,MIN(\"PRODUCT_ID\") AS \"HANDLE_CODE\", MIN(\"PRODUCT_CONTRACT\".\"AID\") AS \"AID\", \n" + 
                            "                                  MIN(\"ITEM_ID\") AS \"ITEM_ID\", \"COMPONENT_ID\" AS \"COMPONENT_ID\",  \n" + 
                            "                                  MIN(\"QUNTITY\") AS \"QUNTITY\", MIN(\"QUNTITY_REPLACED\") AS \"QUNTITY_REPLACED\",   \n" + 
                            "                                                              MIN(\"UNIT_PRICE\") AS \"UNIT_PRICE\",      \n" + 
                            "                                                               MIN(\"CARD NO\") AS \"CARD NO\", MIN(\"BUY_PRICE\") AS \"BUY_PRICE\"  \n" + 
                            "                                                               , MIN(\"PRODUCT_CONTRACT\".\"ASSIGN_ID\") AS \"ASSIGN_ID\", \"Treatment code\",\"Package_NO\",  \n" + 
                            "                                                              MIN(\"DRIV_ID\") AS\"DRIV_ID\" ,MAX(to_char(\"ACTION_DATE\",'DD-MON-YY HH24:mi:SS') )as ACTION_DATE   \n" + 
                            "                                                           ,MIN(\"Quantity_cancel\") AS \"Quantity_cancel\" ,MIN(\"Quantity_Dirty\")  \n" + 
                            "                                                           AS \"Quantity_Dirty\" ,min(\"UNIT_PRICE\") as UNIT_PRICE  \n" + 
                            "                                                           ,MIN(\"PRODUCT_CONTRACT\".\"HANDLE\") AS \"HANDLE\",\"DELIVERY_TYPE\"   \n" + 
                            "                                                               	FROM \"PRODUCT_CONTRACT\" \n" + 
                            "                                                                LEFT JOIN \"ASSIGNMENTS_WORK_XY\" ON \"ASSIGNMENTS_WORK_XY\".\"AID\"=\"PRODUCT_CONTRACT\".\"AID\" \n" + 
                            "                                                                LEFT JOIN \"ASSIGNED_CONTRACT_D\" ON \"ASSIGNED_CONTRACT_D\".\"AID\"=\"PRODUCT_CONTRACT\".\"AID\" \n" + 
                            "                                                                LEFT JOIN \"PRODUCT\" ON \"PRODUCT_HANDLE\"=\"COMPONENT_ID\" \n" + 
                            "                                                                 WHERE \"PRODUCT_CONTRACT\".\"AID\" IN("+Ass_ID+")     \n" + 
                   //         "                                            --                and (\"PRODUCT_CONTRACT\".\"Oracle_flag\"!=1 or \"PRODUCT_CONTRACT\".\"Oracle_flag\" isnull)   \n" + 
                            "                                                             and \"PRODUCT_CONTRACT\".\"HANDLE\" > 0 \n" + 
                            "                                                                 GROUP BY \"COMPONENT_ID\", \"ITEM_ID\",\"Treatment code\",\"Package_NO\",\"DELIVERY_TYPE\",  \n" + 
                            "                                                                      \"PRODUCT_CONTRACT\".\"ASSIGN_ID\",\"ASSIGNMENTS_WORK_XY\".\"CARD_NO\",\"ASSIGNMENTS_WORK_XY\".\"AID\"  \n" + 
                            "                                                                 ORDER BY \"ASSIGNMENTS_WORK_XY\".\"CARD_NO\",\"PRODUCT_CONTRACT\".\"ASSIGN_ID\" \n" + 
                            "                " ;
                            Op_Prod_Str="";
                            Op_pstmt=null;
                            Op_Prod_Str+="INSERT INTO PRODUCT_CONTRACT_D_DOWNLOAD(ASSIGN_ID,ITEM_ID,COMPONENT_ID,QUNTITY,QUNTITY_REPLACED,TREATMENT_CODE,PACKAGE_NO,CARD_NO,QUANTITY_CANCEL,QUANTITY_DIRTY,HANDLE,DELIVERY_TYPE,AID,HANDLE_CODE,UNIT_PRICE,ASSIGNMENT_ID)\n" + 
                            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";   
                            Op_pstmt=OP_Conn.prepareStatement(Op_Prod_Str);
                            DMS_ppstmt=DMS_Conn.prepareStatement(DMS_PSelect_Q);
                            DMS_Prs=DMS_ppstmt.executeQuery();
                            System.err.println("executed");
                            while(DMS_Prs.next()) {
                                Op_pstmt.setObject(1, DMS_Prs.getString(DMS_Prs.findColumn("ASSIGN_ID")));
                                Op_pstmt.setObject(2, DMS_Prs.getString(DMS_Prs.findColumn("ITEM_ID")));
                                Op_pstmt.setObject(3, DMS_Prs.getString(DMS_Prs.findColumn("COMPONENT_ID")));
                                Op_pstmt.setObject(4, DMS_Prs.getInt(DMS_Prs.findColumn("QUNTITY")));
                                Op_pstmt.setObject(5, DMS_Prs.getInt(DMS_Prs.findColumn("QUNTITY_REPLACED")));
                                Op_pstmt.setObject(6, DMS_Prs.getInt(DMS_Prs.findColumn("Treatment code")));
                                Op_pstmt.setObject(7, DMS_Prs.getInt(DMS_Prs.findColumn("Package_NO")));
                                Op_pstmt.setObject(8, DMS_Prs.getInt(DMS_Prs.findColumn("CARD NO")));
                                Op_pstmt.setObject(9, DMS_Prs.getInt(DMS_Prs.findColumn("Quantity_cancel")));
                                Op_pstmt.setObject(10, DMS_Prs.getInt(DMS_Prs.findColumn("Quantity_Dirty")));
                                Op_pstmt.setObject(11, DMS_Prs.getInt(DMS_Prs.findColumn("HANDLE")));
                                Op_pstmt.setObject(12, DMS_Prs.getString(DMS_Prs.findColumn("DELIVERY_TYPE")));
                                Op_pstmt.setObject(13, DMS_Prs.getInt(DMS_Prs.findColumn("AID")));
                                Op_pstmt.setObject(14, DMS_Prs.getString(DMS_Prs.findColumn("HANDLE_CODE")));
                                Op_pstmt.setObject(15, DMS_Prs.getInt(DMS_Prs.findColumn("UNIT_PRICE")));
                                Op_pstmt.setObject(16, DMS_Prs.getInt(DMS_Prs.findColumn("AID")));

                                Op_pstmt.addBatch();
                            }
                            Op_pstmt.executeBatch();
                            OP_Conn.commit();
                        
                        
                        
                        
                        
                        if(Op_pstmt!=null)
                        Op_pstmt.close();
                        if(OP_Conn!=null)
                        OP_Conn.close();
                        if(DMS_Prs!=null)
                        DMS_Prs.close();
                        if(DMS_rs!=null)
                        DMS_rs.close();
                        if(DMS_pstmt!=null)
                        DMS_pstmt.close();
                        if(DMS_ppstmt!=null)
                        DMS_ppstmt.close();
                        if(DMS_Conn!=null)
                        DMS_Conn.close();
                        return true;
                        }
                        
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
        return true;
    }
    
    public boolean DownloadDailyCollectionAssignments(String Assign_date) {
        return true;
    }
    
    public boolean UploadLockedAssignments() {
        Connection DMS_conn=createDMSConnection();
        Connection Operation_Conn=createOperationConnection();
        PreparedStatement DMS_stmt=null;
        PreparedStatement Operation_stmt=null;
        ResultSet DMS_rs=null;
        String Operation_Query="";
        
        String ASS_IDS="";
        if(DMS_conn!=null&&Operation_Conn!=null) {
            String Query="SELECT \"OPERATION_ID\" FROM \"ASSIGNMENTS_WORK_XY\" WHERE \"LOCKED\"=1 and \"ASSIGN_DATE\"::DATE=CURRENT_DATE+1 ";
            try {
                DMS_stmt = DMS_conn.prepareStatement(Query);
                DMS_rs=DMS_stmt.executeQuery();
                while (DMS_rs.next()) {
                    ASS_IDS+=DMS_rs.getString(DMS_rs.findColumn("OPERATION_ID"))+",";
                }
                System.err.println("Ass Id :"+ASS_IDS);
                if(ASS_IDS.length()>0) {
                    ASS_IDS=ASS_IDS.substring(0, ASS_IDS.length()-1);
                    
                }
                Operation_Query="Update contract_delivery_schedule set locked=1 where assignment_id in ("+ASS_IDS+")";
                Operation_stmt=Operation_Conn.prepareStatement(Operation_Query);
               int x= Operation_stmt.executeUpdate();
                System.err.println("Update Result"+x);
                Operation_Conn.commit();
                if(Operation_stmt!=null)
                Operation_stmt.close();
                if(DMS_rs!=null)
                DMS_rs.close();
                if(DMS_stmt!=null)
                DMS_stmt.close();
                if(DMS_conn!=null)
                DMS_conn.close();
                if(Operation_Conn!=null)
                Operation_Conn.close();
                
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }


        }
        return true;
    }
}
