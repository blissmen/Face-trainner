/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainner;

/**
 *
 * @author USER
 */
public class trainee {
    private int TainingCount=0;
    private String TainingDir ="Trainning_Set/";
    private String UserId="Default";
    private Integer camraState;
    
    private int student_status=0;

    public int getStudent_status() {
        return student_status;
    }

    public Integer getCamraState() {
        return camraState;
    }

    public void setCamraState(Integer camraState) {
        this.camraState = camraState;
    }
    
    private void setCamara(Integer valueOf) {
       camraState = valueOf;
    }
    private Integer getCamara() {
      return camraState;
    }
    private int CurrentCount=0;
   
    public int getCurrentCount() {
        return CurrentCount;
    }

    public void setCurrentCount(int CurrentCount) {
        this.CurrentCount = CurrentCount;
    }
    public void setTainingCount(int TainingCount) {
     this.TainingCount = TainingCount;
    }

    public void setTainingDir(String TainingDir) {
        this.TainingDir = TainingDir;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public void setStudent_status(int student_status) {
        this.student_status = student_status;
    }

    public int getTainingCount() {
        return TainingCount;
    }

    public String getTainingDir() {
        return TainingDir;
    }

    public String getUserId() {
        return UserId;
    }

  
}
