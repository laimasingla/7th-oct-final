package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.CustomerBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.dao.BankDao;
import com.cg.ibs.cardmanagement.dao.CustomerDao;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;

public class CustomerServiceImpl implements CustomerService {

	CustomerDao customerDao ;
	

	
	public CustomerServiceImpl() {
		customerDao=new CardManagementDaoImpl();
	
	}

	CustomerBean customObj= new CustomerBean();
		String UCI="7894561239632587";

	String caseIdGenOne = " ";
	String caseIdTotal = " ";
	static int caseIdGenTwo = 0;
	LocalDateTime timestamp;
	LocalDateTime fromDate;
	LocalDateTime toDate;

	String addToQueryTable(String caseIdGenOne) {
		caseIdTotal = caseIdGenOne + caseIdGenTwo;
		caseIdGenTwo++;
		return caseIdTotal;
	}


	public boolean verifyDate(LocalDateTime fromDate2, LocalDateTime toDate2) {
		boolean checkDate = false;
		if (fromDate2.isBefore(toDate2)) {
			checkDate = true;
		}

		return checkDate;
	}

	@Override
	public boolean applyNewDebitCard(BigInteger accountNumber) {
		boolean status=false;
		boolean check = customerDao.verifyAccountNumber(accountNumber);
		
		if (check) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "ANDC";
			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery(accountNumber.toString());
			customerDao.newDebitCard(caseIdObj, accountNumber);
			status=true;
		}

		else {
			status=false;
		}
		return status;

	}

	@Override
	public void applyNewCreditCard() {
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdGenOne = "ANCD";
		timestamp = LocalDateTime.now();

		caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		caseIdObj.setDefineQuery("NA");
		customerDao.newCreditCard(caseIdObj);

	}



	@Override
	public void requestDebitCardLost(BigInteger debitCardNumber) {
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);

		if (check) {
			CaseIdBean caseIdObj = new CaseIdBean();

			caseIdGenOne = "RDCL";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery(debitCardNumber.toString());
			customerDao.requestDebitCardLost(caseIdObj, debitCardNumber);
		} else {
			System.out.println("INVALID DEBIT CARD NUMBER");

		}
	}

	@Override
	public void requestCreditCardLost(BigInteger creditCardNumber) {
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			CaseIdBean caseIdObj = new CaseIdBean();

			caseIdGenOne = "RDCL";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery(creditCardNumber.toString());
			customerDao.requestCreditCardLost(caseIdObj, creditCardNumber);
		} else {
			System.out.println("INVALID CREDIT CARD NUMBER");

		}
	}

	
	
	

	public void raiseDebitMismatchTicket(String transactionId) {
	
		boolean transactionResult = customerDao.verifyDebitTransactionId(transactionId);
		if (transactionResult) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "RDMT";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery(transactionId.toString());

			customerDao.raiseDebitMismatchTicket(caseIdObj, transactionId);
		} else {
			System.out.println("INVALID TRANSACTION ID");

		}
		
	}

	public List<DebitCardBean> viewAllDebitCards() {

		return customerDao.viewAllDebitCards();
	}

	@Override
	public List<CreditCardBean> viewAllCreditCards() {

		return customerDao.viewAllCreditCards();

	}

	
	
	
	public String verifyDebitcardType(BigInteger debitCardNumber) {
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (check) {
			String type = customerDao.getdebitCardType(debitCardNumber);
			return type;
		} else {
			System.out.println("INVALID DEBIT CARD NUMBER");
			return null;
		}
	}

	@Override
	public void requestDebitCardUpgrade(BigInteger debitCardNumber,int myChoice) {
	
		CaseIdBean caseIdObj = new CaseIdBean();
		
		caseIdGenOne = "RDCU";
		timestamp = LocalDateTime.now();

		caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Upgrade to Gold");
			customerDao.requestDebitCardUpgrade(caseIdObj, debitCardNumber);
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Upgrade to Platinum");
			customerDao.requestDebitCardUpgrade(caseIdObj, debitCardNumber);
		} else {
			System.out.println("Choose a valid option");
		}

	}



	
	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) {

		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		return (check);
	}
	
	public boolean verifyDebitPin(int pin, BigInteger debitCardNumber) {
		if (pin == customerDao.getDebitCardPin(debitCardNumber)) {

			return true;
		} else {
			return false;
		}
	}
	
	public void resetDebitPin(BigInteger debitCardNumber, int newPin) {

		customerDao.setNewDebitPin(debitCardNumber, newPin);
		System.out.println("PIN UPDATED SUCCESSFULLY!");
	}
	
	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) {

		boolean check1 = customerDao.verifyCreditCardNumber(creditCardNumber);
		return (check1);
	}
	
	public boolean verifyCreditPin(int pin, BigInteger creditCardNumber) {
		if (pin == customerDao.getCreditCardPin(creditCardNumber)) {

			return true;
		} else {
			return false;
		}
	}
	
	public void resetCreditPin(BigInteger creditCardNumber, int newPin) {

		customerDao.setNewCreditPin(creditCardNumber, newPin);
		System.out.println("PIN UPDATED SUCCESSFULLY!");
	}

	@Override
	public void requestCreditCardUpgrade(BigInteger creditCardNumber,int myChoice) {
	

		
		caseIdGenOne = "RCCU";
		timestamp = LocalDateTime.now();
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Upgrade to Gold");
			customerDao.requestCreditCardUpgrade(caseIdObj, creditCardNumber);
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Upgrade to Platinum");
			customerDao.requestCreditCardUpgrade(caseIdObj, creditCardNumber);
		} else {
			System.out.println("Choose a valid option");
		}
		

	}

	@Override
	public String verifyCreditcardType(BigInteger creditCardNumber) {
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			String type = customerDao.getcreditCardType(creditCardNumber);
			return type;
		} else {
			System.out.println("INVALID CREDIT CARD NUMBER");
			return null;
		}

	}



	@Override
	public void raiseCreditMismatchTicket(String transactionId) {
		
		boolean transactionResult = customerDao.verifyCreditTransactionId(transactionId);
		if (transactionResult) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "RCMT";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery(transactionId.toString());

			customerDao.raiseCreditMismatchTicket(caseIdObj, transactionId);
		} else {
			System.out.println("INVALID TRANSACTION ID");

		}
		
	}
	
	
	public List<DebitCardTransaction> getDebitTrns(int dys, BigInteger debitCardNumber) {
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (check){
		    if(dys>=1) {
			    
		    	return customerDao.getDebitTrans(dys, debitCardNumber);
		    }
		    else {
		    	System.out.println("invalid days format");
		    	return null;
		    }
		
	    }
		else {
			System.out.println("debit card does not exist");
		return null;
	   }
	}

	@Override
	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) {
	
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check){
		    if(days>=1) {
			    
		    	return customerDao.getCreditTrans(days, creditCardNumber);
		    }
		    else {
		    	System.out.println("invalid days format");
		    	return null;
		    }
		
	    }
		else {
			System.out.println("credit card does not exist");
		return null;
	   }
	}

}