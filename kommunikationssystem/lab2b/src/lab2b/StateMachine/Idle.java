/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2b.StateMachine;
/**
 *
 * @author Michael
 */
public class Idle extends State {
    
    
   public State initiateCall(){
       return new CallingOut();
   }
   
   public State inviteTRO(){
       return new CallingIn();
   } 

    @Override
    public String getStatename() {
        return "Idle";
    }
    
    
}