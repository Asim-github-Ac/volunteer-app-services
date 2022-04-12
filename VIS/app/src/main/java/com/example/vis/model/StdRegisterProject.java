package com.example.vis.model;

public class StdRegisterProject {
    String orgemail,orgname,orgslots,stdemail;

    public StdRegisterProject(String orgemail, String orgname, String orgslots, String stdemail) {
        this.orgemail = orgemail;
        this.orgname = orgname;
        this.orgslots = orgslots;
        this.stdemail = stdemail;
    }

    public StdRegisterProject() {
    }

    public String getOrgemail() {
        return orgemail;
    }

    public void setOrgemail(String orgemail) {
        this.orgemail = orgemail;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgslots() {
        return orgslots;
    }

    public void setOrgslots(String orgslots) {
        this.orgslots = orgslots;
    }

    public String getStdemail() {
        return stdemail;
    }

    public void setStdemail(String stdemail) {
        this.stdemail = stdemail;
    }
}
