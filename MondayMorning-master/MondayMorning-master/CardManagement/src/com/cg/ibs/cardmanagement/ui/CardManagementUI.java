package com.cg.ibs.cardmanagement.ui;

import java.util.*;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.service.BankService;
import com.cg.ibs.cardmanagement.service.BankServiceClassImpl;
import com.cg.ibs.cardmanagement.service.CustomerService;
import com.cg.ibs.cardmanagement.service.CustomerServiceImpl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CardManagementUI {

	static BigInteger accountNumber = null;
	static Scanner scan;

    static boolean success=false;
    
	public void doIt() {
		while (true) {

			CustomerService customService = new CustomerServiceImpl();
			BankService bankService = new BankServiceClassImpl();
			System.out.println("Welcome to card management System");
			System.out.println("Enter 1 to login as a customer");
			System.out.println("Enter 2 to login as a bank admin");

			int userInput = scan.nextInt();

			if (userInput == 1) {
				System.out.println("You are logged in as a customer");
				CustomerMenu choice = null;
				while (choice != CustomerMenu.CUSTOMER_LOG_OUT) {
					System.out.println("Menu");
					System.out.println("--------------------");
					System.out.println("Choice");
					System.out.println("--------------------");
					for (CustomerMenu mmenu : CustomerMenu.values()) {
						System.out.println(mmenu.ordinal() + "\t" + mmenu);
					}
					System.out.println("Choice");

					int ordinal = scan.nextInt();
					if (ordinal >= 0 && ordinal < 15) {
						choice = CustomerMenu.values()[ordinal];

						switch (choice) {

						case LIST_EXISTING_DEBIT_CARDS:
							
							
							List<DebitCardBean> debitCardBeans = customService.viewAllDebitCards();
							if(debitCardBeans.isEmpty()){
								System.out.println("No Existing Debit Cards");
							}
							else{
						   for (DebitCardBean debitCardBean : debitCardBeans) {
								System.out.println(debitCardBeans.toString());
							}}
							break;

						case LIST_EXISTING_CREDIT_CARDS:

							List<CreditCardBean> creditCardBeans = customService.viewAllCreditCards();
							if(creditCardBeans.isEmpty()){
								System.out.println("No Existing Credit Cards");
							}
							else{
							for (CreditCardBean creditCardBean : creditCardBeans) {
								System.out.println(creditCardBeans.toString());
							}
							}
							break;

						case APPLY_NEW_DEBIT_CARD:
							 success=false;
							System.out.println("You are applying for a new Debit Card");
							while(!success){
							
							try {
								System.out.println("Enter Account Number you want to apply debit card for :");

								 accountNumber = scan.nextBigInteger();
							
								success = true;
							} catch (InputMismatchException wrongFormat) {
								
								
								scan.next();
								System.out.println("Renter 10 digit account number");
							}}
							
							boolean result = customService.applyNewDebitCard(accountNumber);
							System.out.println(result);
							if (result) {
								System.out.println(" Debit Card applied successfully");
							} else {
								System.out.println("Account Number Entered wrong");
							}
							
							break;
						case APPLY_NEW_CREDIT_CARD:

							System.out.println("You are applying for a new Credit Card");
							
							customService.applyNewCreditCard();
							System.out.println("New Credit Card applied successfully");
							break;
						
						
						
						case UPGRADE_EXISTING_DEBIT_CARD:
							System.out.println("Enter your Debit Card Number: ");
							
						BigInteger debitCardNumber=null;
						 success=false;
							int myChoice;
							while(!success){
								
								try{
						 debitCardNumber = scan.nextBigInteger();
						 System.out.println(debitCardNumber);
							success=true;
								}
								catch (InputMismatchException wrongFormat) {
									scan.next();
								System.out.println("Enter a valid debit card number");
								}
							String type = customService.verifyDebitcardType(debitCardNumber);
							System.out.println("Your debit card is:" + type);

							if (type.equals("Silver")) {
								System.out.println("Choose 1 to upgrade to Gold");
								System.out.println("Choose 2 to upgrade to Platinum");
								myChoice = scan.nextInt();
								customService.requestDebitCardUpgrade(debitCardNumber, myChoice);

							} else if (type.equals("Gold")) {
								System.out.println("Choose 2 to upgrade to Platinum");
								myChoice = scan.nextInt();
								customService.requestDebitCardUpgrade(debitCardNumber, myChoice);

							} else {
								System.out.println("You already have a Platinum Card");
							}}

							break;
						case UPGRADE_EXISTING_CREDIT_CARD:
							System.out.println("Enter your Credit Card Number: ");
							while(!success){
								BigInteger creditCardNumber=null;
							
							int myChoice1;
							try{
							creditCardNumber = scan.nextBigInteger();
							success = true;}
							catch(InputMismatchException wrongFormat){

								scan.next();
								System.out.println("Renter  credit  number");
								
							}
							String type1 = customService.verifyCreditcardType(creditCardNumber);
							System.out.println("Your credit card is:" + type1);

							if (type1.equals("Silver")) {
								System.out.println("Choose 1 to upgrade to Gold");
								System.out.println("Choose 2 to upgrade to Platinum");
								myChoice1 = scan.nextInt();
								customService.requestCreditCardUpgrade(creditCardNumber, myChoice1);

							} else if (type1.equals("Gold")) {
								System.out.println("Choose 2 to upgrade to Platinum");
								myChoice1 = scan.nextInt();
								customService.requestCreditCardUpgrade(creditCardNumber, myChoice1);

							} else {
								System.out.println("You already have a Platinum Card");
							}
							}
							break;
						case RESET_DEBIT_CARD_PIN:
							success=false;
							
						
							while(!success){
							
								try{
							debitCardNumber = scan.nextBigInteger();
							success=true;
								}
								catch (InputMismatchException wrongFormat) {
									scan.next();
								System.out.println("Enter a valid debit card number");
								}
							
							System.out.println("Enter your Debit Card Number: ");
							debitCardNumber = scan.nextBigInteger();
							boolean check = customService.verifyDebitCardNumber(debitCardNumber);
							if (check) {
								System.out.println("Enter your existing pin:");
								int pin = scan.nextInt();
								if (customService.verifyDebitPin(pin, debitCardNumber)) {
									System.out.println("Enter new pin");
									int newPin = scan.nextInt();
									customService.resetDebitPin(debitCardNumber, newPin);
								} else {
									System.out.println("Invalid pin entered");
								}

							} else {
								System.out.println("Invalid debit card number");
							}
							}
							break;
						case RESET_CREDIT_CARD_PIN:
							System.out.println("Enter your Credit Card Number: ");
							creditCardNumber = scan.nextBigInteger();
							boolean check1 = customService.verifyCreditCardNumber(creditCardNumber);
							if (check1) {
								System.out.println("Enter your existing pin:");
								int pin = scan.nextInt();
								if (customService.verifyCreditPin(pin, creditCardNumber)) {
									System.out.println("Enter new pin");
									int newPin = scan.nextInt();
									customService.resetCreditPin(creditCardNumber, newPin);
								} else {
									System.out.println("Invalid pin entered");
								}

							} else {
								System.out.println("Invalid credit card number");
							}
							break;
						case REPORT_DEBIT_CARD_LOST:
							System.out.println("Enter your Debit Card Number: ");
							success=false;
							while(!success){
							
								try{
							debitCardNumber = scan.nextBigInteger();
							success=true;
								}
								catch (InputMismatchException wrongFormat) {
									scan.next();
								System.out.println("Enter a valid debit card number");
								}
							debitCardNumber = scan.nextBigInteger();
							customService.requestDebitCardLost(debitCardNumber);}
							break;
						case REPORT_CREDIT_CARD_LOST:
							System.out.println("Enter your Credit Card Number: ");

							creditCardNumber = scan.nextBigInteger();
							customService.requestCreditCardLost(creditCardNumber);
							break;
						case REQUEST_DEBIT_CARD_STATEMENT:
							System.out.println("Enter your Debit Card Number: ");
							success=false;
							while(!success){
								
								try{
							debitCardNumber = scan.nextBigInteger();
							success=true;
								}
								catch (InputMismatchException wrongFormat) {
									scan.next();
								System.out.println("Enter a valid debit card number");
								}
							debitCardNumber = scan.nextBigInteger();

							System.out.println("enter days : ");
							int days = scan.nextInt();

							try {
								List<DebitCardTransaction> debitCardBeanTrns = customService.getDebitTrns(days,
										debitCardNumber);
								for (DebitCardTransaction debitCardTrns : debitCardBeanTrns)
									System.out.println(debitCardTrns.toString());
							}

							catch (Exception e) {
								System.out.println("invalid date format or debit card does not exist");
							}}
							break;
						case REQUEST_CREDIT_CARD_STATEMENT:
							System.out.println("Enter your Credit Card Number: ");
							creditCardNumber = scan.nextBigInteger();
							System.out.println("enter days : ");
							int days = scan.nextInt();

							try {
								List<CreditCardTransaction> creditCardBeanTrans = customService.getCreditTrans(days,
										creditCardNumber);
								for (CreditCardTransaction creditCardTrans : creditCardBeanTrans)
									System.out.println(creditCardTrans.toString());
							}

							catch (Exception e) {
								System.out.println("invalid date format or credit card does not exist");
							}
							break;
						case REPORT_DEBITCARD_STATEMENT_MISMATCH:

							String transactionId1;
							System.out.println("Enter your transaction id");
							transactionId1 = scan.next();
							customService.raiseDebitMismatchTicket(transactionId1);
							break;
						case REPORT_CREDITCARD_STATEMENT_MISMATCH:
							String transactionId;
							System.out.println("Enter your transaction id");
							transactionId = scan.next();
							customService.raiseCreditMismatchTicket(transactionId);
							break;
						case CUSTOMER_LOG_OUT:
							System.out.println("LOGGED OUT");
							break;
						}
					}

				}
			} else {
				if (userInput == 2) {

					System.out.println("You are logged in as a Bank Admin");
					BankMenu cchoice = null;
					while (cchoice != BankMenu.BANK_LOG_OUT) {
						System.out.println("Menu");
						System.out.println("--------------------");
						System.out.println("Choice");
						System.out.println("--------------------");
						for (BankMenu mmenu : BankMenu.values()) {
							System.out.println(mmenu.ordinal() + "\t" + mmenu);
						}
						System.out.println("Choice");
						int ordinal = scan.nextInt();
						if (ordinal >= 0 && ordinal < BankMenu.values().length) {
							cchoice = BankMenu.values()[ordinal];

							switch (cchoice) {

							case LIST_QUERIES:
								List<CaseIdBean> caseBeans = bankService.viewQueries();

								for (CaseIdBean caseId : caseBeans) {

									System.out.println(caseId.toString());
								}
								break;

							case REPLY_QUERIES:
								System.out.println("Enter query ID ");
								String queryId = scan.next();
								if (bankService.verifyQueryId(queryId)) {
									System.out.println("Enter new Status");
									String newStatus = scan.next();
									bankService.setQueryStatus(queryId, newStatus);

								}

								break;
							case VIEW_DEBIT_CARD_STATEMENT:
								System.out.println("Enter your Debit Card Number: ");
								BigInteger debitCardNumber = scan.nextBigInteger();

								System.out.println("enter days : ");
								int days = scan.nextInt();

								try {
									List<DebitCardTransaction> debitCardBeanTrns = bankService.getDebitTrns(days,
											debitCardNumber);
									for (DebitCardTransaction debitCardTrns : debitCardBeanTrns)
										System.out.println(debitCardTrns.toString());
								}

								catch (Exception e) {
									System.out.println("invalid date format or debit card does not exist");
								}

								break;
							case VIEW_CREDIT_CARD_STATEMENT:
								System.out.println("Enter your Credit Card Number: ");
								BigInteger creditCardNumber = scan.nextBigInteger();
								System.out.println("enter days : ");
								days = scan.nextInt();

								try {
									List<CreditCardTransaction> creditCardBeanTrans = bankService.getCreditTrans(days,
											creditCardNumber);
									for (CreditCardTransaction creditCardTrans : creditCardBeanTrans)
										System.out.println(creditCardTrans.toString());
								}

								catch (Exception e) {
									System.out.println("invalid date format or credit card does not exist");
								}

								break;
							case BANK_LOG_OUT:
								System.out.println("LOGGED OUT");
								break;

							}
						}
					}
				} else {
					System.out.println("Invalid Option!!");

				}

			}

		}
	}

	public static void main(String args[]) throws Exception {
		scan = new Scanner(System.in);
		CardManagementUI obj = new CardManagementUI();
		obj.doIt();
		System.out.println("Program End");
		obj.scan.close();
	}
}