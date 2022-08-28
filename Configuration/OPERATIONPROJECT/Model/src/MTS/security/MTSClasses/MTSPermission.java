package MTS.security.MTSClasses;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Locale;
import java.util.Map;

import java.util.Set;

//import org.apache.commons.lang.LocaleUtils;
import javax.faces.context.FacesContext;

import javax.mail.Folder;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;
import oracle.jbo.domain.Number;

import oracle.jheadstart.controller.jsf.bean.LocaleManager;
import oracle.jheadstart.controller.jsf.util.JsfUtils;


public class MTSPermission extends HashMap {

   
    private Number Org_unit_id;
    private int userId;
 //   private Number NotificationCount;
    private String currentuser;
    private String userDisplayName;
 //   private  oracle.jbo.domain.Number buId;
    private List permissions;
    private List subsystems;
    private List modules;
    private List modules_ar;
    private ArrayList<Integer> modulesId;
    private HashMap<String, String> modulePaths;
    private HashMap<String, String> moduleArPaths;
    private String serverIp;
    private String serverPort;
    private String serverUsername;
    private String serverPassword;
    private Integer currentModuleId = 20;
    
  //  private List roles;
 //   private String UserManagedOrganization;

    /**
     * @param key
     * @return
     */
    
    public static MTSPermission getInstance()
    {
      MTSPermission instance;
      Object value = JsfUtils.getExpressionValue("#{MTSPermission}");
      if (value instanceof MTSPermission)
      {
        instance = (MTSPermission)value;
      }
      else
      {
        instance = new MTSPermission();
      }
      return instance;
    }
    public MTSPermission() {
        getUserPermissions();
      //  applyUserPreferencies();
    }
    
 
    public Object get(Object key) {
          AdministrationBean b = new AdministrationBean();
          if(b.getUser().compareTo(getCurrentuser())!=0) {
              getUserPermissions();
          }
          if(key.equals("isAuthorized"))
          {
              if(getIsAuthorized())
                  return true;
          }
          return userHasPermission(key);

        //return false;
    }

 
  public void setCurrentuser(String currentuser) {
      this.currentuser = currentuser;
  }
  
  public String getCurrentuser() {
      return currentuser;
  }
  
  public void setUserDisplayName(String userDisplayName) {
      this.userDisplayName = userDisplayName;
  }
  
  public String getUserDisplayName() {
      return userDisplayName;
  }

  public void setPermissions(List permissions) {
      this.permissions = permissions;
  }
  
  public List getPermissions() {
      return permissions;
  }
  
  public void setSubsystems(List subsystems) {
      this.subsystems = subsystems;
  }
  
  public List getSubsystems() {
      return subsystems;
  }
  
  public void setModules(List modules) {
      this.modules = modules;
  }
  
  public List getModules() {
      return modules;
  }
  
  public void setModulesId(ArrayList<Integer> modulesId) {
      this.modulesId = modulesId;
  }
  
  public ArrayList<Integer> getModulesId() {
      return modulesId;
  }
   
public void resetModuleList()
{
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("MTSPermission", null);

        MTSPermission mtsper = new MTSPermission();
        modulesId = new ArrayList<Integer>();
        mtsper.getUserPermissions();
    }

    @SuppressWarnings("unchecked")
    private void getUserPermissions() { 
        AdministrationBean b = new AdministrationBean();
        setCurrentuser(b.getUser());
        permissions = new ArrayList();
 //       modules = new ArrayList();
 //       modules_ar = new ArrayList();
        modulesId = new ArrayList<Integer>();
        subsystems = new ArrayList();
        modulePaths = new HashMap<String, String>();
        moduleArPaths = new HashMap<String, String>();
        String scUser = "SELECT USER_ID, USER_NAME, DISPLAY_NAME, ORG_UNIT_ID FROM SC_USERS WHERE USER_NAME=?";
        String scUserPermission = "SELECT USER_ID, USER_NAME, PERMISSION_ID, PERMISSION_NAME FROM SC_USER_PERMISSION WHERE USER_ID=? AND MODULE_ID=?";
        String scUserModule = "SELECT USER_ID, USER_NAME, MODULE_ID, MODULE_NAME, MODULE_NAME_AR, PATH FROM SC_USER_MODULE WHERE USER_ID=?";
        String scUserSubsystem = "SELECT USER_ID, USER_NAME, SUBSYS_ID, SUBSYS_NAME FROM SC_USER_SUBSYSTEM  WHERE USER_ID=?";
       // String weblogicConf = "SELECT SERVER_IP, SERVER_PORT, SERVER_USERNAME, SERVER_PASSWORD, MAINSCREEN_IP, MAINSCREEN_PORT FROM WEB_LOGIC_CONF";
        Connection conn  = null ;
        PreparedStatement pstmt = null;
        ResultSet res = null ;
        try 
        {
             conn = createConnection();
             pstmt = conn.prepareStatement(scUser);
             pstmt.setString(1, getCurrentuser());
             res = pstmt.executeQuery();
             while(res.next()) {
                 setUserId(res.getInt("USER_ID"));
                 setUserDisplayName(res.getString("DISPLAY_NAME"));
                 setOrg_unit_id(new Number(res.getInt("ORG_UNIT_ID")));
             }
            pstmt = conn.prepareStatement(scUserPermission);
            pstmt.setInt(1, getUserId());
            pstmt.setObject(2, currentModuleId);
            res = pstmt.executeQuery();
            while(res.next()) {
                permissions.add(res.getObject("PERMISSION_NAME"));
            }
            pstmt = conn.prepareStatement(scUserModule);
            pstmt.setInt(1, getUserId());
            res = pstmt.executeQuery();
            System.out.println("before res.next()");
            while(res.next()) {
                modulesId.add(res.getInt("MODULE_ID"));
         //       modules_ar.add(res.getObject("MODULE_NAME_AR"));
         //       modules.add(res.getObject("MODULE_NAME"));
                modulePaths.put(res.getString("MODULE_NAME"), res.getString("PATH"));
                moduleArPaths.put(res.getString("MODULE_NAME_AR"), res.getString("PATH"));
            }
            modules = new ArrayList(modulePaths.keySet());
            modules_ar = new ArrayList(moduleArPaths.keySet());
            pstmt = conn.prepareStatement(scUserSubsystem);
            pstmt.setInt(1, getUserId());
            res = pstmt.executeQuery();
            while(res.next()) {
                subsystems.add(res.getObject("SUBSYS_NAME"));
            }
//            pstmt = conn.prepareStatement(weblogicConf);
//            res = pstmt.executeQuery();
//            while(res.next()) {
//                serverIp = res.getString("SERVER_IP");
//                serverPort = res.getString("SERVER_PORT");
//                serverUsername = res.getString("SERVER_USERNAME");
//                serverPassword = res.getString("SERVER_PASSWORD");
//            }
             
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            

        }
      //        permissions = new ArrayList();
 //             subsystems = new ArrayList();
 //           modules = new ArrayList();
 //           modules_ar = new ArrayList();
 //           modulesId = new ArrayList();
 //           SecurityAppModule am = null;
 //           try
 //           {
 //           am =
 //               (SecurityAppModule)Configuration.createRootApplicationModule("MTS.security.SecurityAppModule", "SecurityAppModuleLocal");
 //           ViewObject userVo = am.findViewObject("ScUserView1");
 //           userVo.executeQuery();
 //           while(userVo.hasNext()) {
 //             Row userRow = userVo.next();
 //         //      setUserDisplayName(userRow.getAttribute("DisplayName").toString());
 //         /*      RowSet permissionVo = (RowSet)userRow.getAttribute("ScUserPermissionsView");
 //               while(permissionVo.hasNext()) {
 //                 Row permissionRow = permissionVo.next();
 //                   permissions.add(permissionRow.getAttribute("PermissionName"));
 //               }*/
 //     /*        RowSet subsystemVo = (RowSet)userRow.getAttribute("ScUserSubsystemsView");
 //             while(subsystemVo.hasNext()) {
 //               Row subsystemRow = subsystemVo.next();
 //                 subsystems.add(subsystemRow.getAttribute("SubsysName").toString());
 //             }*/
 //     /*        RowSet moduleVo = (RowSet)userRow.getAttribute("ScUserModulesView");
//          
 //             while(moduleVo.hasNext()) {
 //               Row moduleRow = moduleVo.next();
//                if( FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()=="en")
//                {
//                  modules.add(moduleRow.getAttribute("ModuleName").toString());
//                }
//                  else {
//                  modules.add(moduleRow.getAttribute("ModuleNameAr").toString());
//                }
//                modulesId.add(moduleRow.getAttribute("ModuleId").toString());
//                 modules_ar.add(moduleRow.getAttribute("ModuleNameAr").toString());
//                 modules.add(moduleRow.getAttribute("ModuleName").toString());
//             }
//*/               
//   /*           if(userRow.getAttribute("Org_unit_id")!=null)
//                  setOrg_unit_id((Number)(userRow.getAttribute("Org_unit_id")));
//              else
//              {
//                  setOrg_unit_id(null);
//              */
//         /*     ViewObject notificationVo = am.findViewObject("MohFacilityNotificationView1");
//              Row[] notification = notificationVo.getFilteredRows("FacilityId", getBuId());
//                if(notification.length>0) 
//                  setNotificationCount((Number)notification[0].getAttribute("Count1"));
//                else
//                    setNotificationCount(new Number(0));
//            }
//            //}
//
//         */ 
//            }}
//            catch(Exception e) {
//                  e.printStackTrace();
//                }
//            finally
//            {
//              Configuration.releaseRootApplicationModule(am, true);
//            }

          
            
            }
    
    public String getModulePath(String ModuleName) {
        Object path = null;
//      SecurityAppModule am =
//          (SecurityAppModule)Configuration.createRootApplicationModule("MTS.security.SecurityAppModule", "SecurityAppModuleLocal");
//      ViewObject userVo = am.findViewObject("ScUserModulesView1");
//      Row[] r = null;
//      if(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()=="en") {
//        r = userVo.getFilteredRows("ModuleName", ModuleName);
//      }
//      else
//      {
//         r = userVo.getFilteredRows("ModuleNameAr", ModuleName);
//      }    
//      if(r.length>0) {
//        path = r[0].getAttribute("Path");
//      }
//      Configuration.releaseRootApplicationModule(am, true);
      if(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()=="en") {
        path = modulePaths.get(ModuleName);
      }
      else {
          path = moduleArPaths.get(ModuleName);
      }
      if(path!=null)
      return path.toString();
      else
      return null;
    }
    
    public boolean getIsAuthorized() {
        return modulesId.contains(currentModuleId);

  /*    for (int i=0;i<modulesId.size();i++)
      {
          if(modulesId.get(i).equals("1022"))
          {
              return true;
          }
      }
      return false;*/
    }
    
    public boolean userHasPermission(Object permission) {
        return permissions.contains(permission);
 /*     for(int i=0; i<permissions.size();i++) {
        if(permissions.get(i).equals(permission))
            return true;
      }
      return false;*/
    }

/*    public void setBuId(Number buId) {
        this.buId = buId;
    }

    public Number getBuId() {
        return buId;
    }

*/

        
    public void setOrg_unit_id(Number Org_unit_id) {
          this.Org_unit_id = Org_unit_id;
      }

      public Number getOrg_unit_id() {
          return Org_unit_id;
      }

/*
      public void setNotificationCount(Number NotificationCount) {
          this.NotificationCount = NotificationCount;
      }

      public Number getNotificationCount() {
          return NotificationCount;
      }
*/
      
    public void setModules_ar(List modules_ar) {
        this.modules_ar = modules_ar;
    }

    public List getModules_ar() {
        return modules_ar;
    }

    private Connection createConnection() {

        Connection conn = null;
            try {
                InitialContext ic = new InitialContext();
                javax.sql.DataSource ds =
                    (DataSource)ic.lookup("java:comp/env/jdbc/AdminDS");
                conn = ds.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setModulePaths(HashMap<String, String> modulePaths) {
        this.modulePaths = modulePaths;
    }

    public HashMap<String, String> getModulePaths() {
        return modulePaths;
    }

    public void setModuleArPaths(HashMap<String, String> moduleArPaths) {
        this.moduleArPaths = moduleArPaths;
    }

    public HashMap<String, String> getModuleArPaths() {
        return moduleArPaths;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerUsername(String serverUsername) {
        this.serverUsername = serverUsername;
    }

    public String getServerUsername() {
        return serverUsername;
    }

    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
    }

    public String getServerPassword() {
        return serverPassword;
    }
}

