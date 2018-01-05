/**
 * Created by admin on 08/11/2017.
 */

package com.example.parij.myschoolcomm;
public class authorizeclass
{
    String mname;
    String mcontact;
    String relation;
    String startdate;
    String enddate;

    public authorizeclass()
    {




    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMcontact() {
        return mcontact;
    }

    public void setMcontact(String mcontact) {
        this.mcontact = mcontact;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public authorizeclass(String name, String mcontact, String relation,String startdate,String enddate)
    {
        this.mcontact=mcontact;
        this.mname=name;
        this.relation=relation;
        this.startdate=startdate;
        this.enddate=enddate;

    }

}