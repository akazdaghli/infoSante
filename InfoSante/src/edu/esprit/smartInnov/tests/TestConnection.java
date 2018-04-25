package edu.esprit.smartInnov.tests;

import java.sql.Connection;

import edu.esprit.smartInnov.utils.ConnectionManager;

public class TestConnection {

	public static void main(String[] args) {
		Connection cnx = ConnectionManager.getInstance().getCnx();
	}
}
