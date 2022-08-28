package MTS.security.MTSClasses;


import java.io.Serializable;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.domain.Number;


public class AdministrationBean  implements Serializable {
  public AdministrationBean() {
      
    
      
  }
    String UserDisplayNameFull;
  public String getLocle() {
  String currentlanguage ="en";

  if(ADFContext.getCurrent().getSessionScope().get("currentlanguage") != null) {
  currentlanguage = (String)ADFContext.getCurrent().getSessionScope().get("currentlanguage");
  }
  return currentlanguage ;
  }

  public String getUser() {
      return ADFContext.getCurrent().getSecurityContext().getUserName();
 }

  public String getUserDisplayName() {
	  try{
    //   MTSPermission per = new MTSPermission();
        MTSPermission per = MTSPermission.getInstance();
        if(per.getUserDisplayName()==null)
            per = new MTSPermission();
      if(!(getUser().equalsIgnoreCase("anonymous")  ))
       {
          if( 
              per.getUserDisplayName().isEmpty() || 
              per.getUserDisplayName()==""  || 
              per.getUserDisplayName()==null
              )
             return "";
      setUserDisplayNameFull(per.getUserDisplayName());
       return (per.getUserDisplayName().length()>30?per.getUserDisplayName().substring(0, 30)+".." 
           :per.getUserDisplayName()
           );
       }
       else 
       return "";
	  }catch(Exception e)
	  { return ""; }
  }
  

    public String loginChecker() {
        // Add event code here...
        return null;
    }

    public void setUserDisplayNameFull(String UserDisplayNameFull) {
        this.UserDisplayNameFull = UserDisplayNameFull;
    }

    public String getUserDisplayNameFull() {
        return UserDisplayNameFull;
    }
}
