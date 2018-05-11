package edu.esprit.smartInnov.tests;

import java.sql.Connection;
import java.util.Random;

import edu.esprit.smartInnov.utils.ConnectionManager;

public class TestConnection {

//	public static void main(String[] args) {
//		Connection cnx = ConnectionManager.getInstance().getCnx();
//	}
	
	public static void main(String[] args) {
		  String aToZ="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // 36 letter.
		  String randomStr=generateRandom(aToZ);
		  System.out.println(randomStr);

		}

		private static String generateRandom(String aToZ) {
		    Random rand=new Random();
		    StringBuilder res=new StringBuilder();
		    for (int i = 0; i < 17; i++) {
		       int randIndex=rand.nextInt(aToZ.length()); 
		       res.append(aToZ.charAt(randIndex));            
		    }
		    return res.toString();
		}
}
