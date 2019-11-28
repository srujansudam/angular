/*
 * package com.cg.ibs.rm.ui;
 * 
 * import java.io.BufferedReader; import java.io.IOException; import
 * java.io.InputStreamReader; import java.math.BigDecimal; import
 * java.math.BigInteger; import java.time.LocalDate; import
 * java.time.LocalDateTime; import java.time.format.DateTimeFormatter; import
 * java.util.Iterator; import java.util.Scanner; import java.util.Set; import
 * java.util.regex.Pattern;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.ApplicationContext; import
 * org.springframework.context.support.ClassPathXmlApplicationContext; import
 * org.springframework.stereotype.Component;
 * 
 * import com.cg.ibs.rm.exception.IBSExceptions; import
 * com.cg.ibs.rm.model.AccountBean; import com.cg.ibs.rm.model.AutoPayment;
 * import com.cg.ibs.rm.model.Beneficiary; import
 * com.cg.ibs.rm.model.CreditCard; import com.cg.ibs.rm.model.ServiceProvider;
 * import com.cg.ibs.rm.model.ServiceProviderId; import
 * com.cg.ibs.rm.service.AccountService; import
 * com.cg.ibs.rm.service.AutoPaymentService; import
 * com.cg.ibs.rm.service.Bank_AdminService; import
 * com.cg.ibs.rm.service.BeneficiaryAccountService; import
 * com.cg.ibs.rm.service.CreditCardService; import
 * com.cg.ibs.rm.service.CustomerService;
 * 
 * @Component("main") public class MainUi { private static Scanner scanner;
 * private static ApplicationContext context; private static MainUi main;
 * 
 * @Autowired private CreditCardService cardService;
 * 
 * @Autowired private BeneficiaryAccountService beneficiaryAccountService;
 * 
 * @Autowired private Bank_AdminService bankRepresentativeService;
 * 
 * @Autowired private AutoPaymentService autopaymentservice; private
 * BufferedReader keyboardInput = new BufferedReader(new
 * InputStreamReader(System.in)); private BigInteger uci;
 * 
 * @Autowired private CustomerService customerService;
 * 
 * @Autowired private AccountService accountService;
 * 
 * private String message1 = "------------------------"; private String message2
 * = "Please enter a valid option."; private String message3 = "Choices:";
 * private String message4 = "BACK ON HOME PAGE!!"; private String message5 =
 * "---------------------------------------------------------------------------------------";
 * private String message6 =
 * "-----------------------------------------------------------------------------------------------------------------------";
 * private String message7 =
 * "----------------------------------------------------------------------------------------------";
 * 
 * private void start() { MainMenu choice = null; while (MainMenu.QUIT !=
 * choice) { System.out.println(message1);
 * System.out.println("Choose your identity from MENU:");
 * System.out.println(message1); for (MainMenu menu : MainMenu.values()) {
 * System.out.println((menu.ordinal() + 1) + "\t" + menu); }
 * System.out.println("Your choice :");// choosing of identity whether user or
 * bank representative
 * 
 * int ordinal = choice(scanner) - 1; if (0 <= (ordinal) &&
 * MainMenu.values().length > ordinal) { choice = MainMenu.values()[ordinal];
 * switch (choice) { case CUSTOMER: login(); customerAction(); break; case
 * BANK_ADMIN: bankRepresentativeAction(); break; case QUIT:
 * System.out.println("Thankyou... Visit again!"); break; } } else {
 * System.out.println(message2); choice = null; } } }
 * 
 * private void login() {// to enter login details int test; String tempUci; do
 * { test = 0; System.out.println("Customer id:"); try { tempUci =
 * keyboardInput.readLine(); if ((Pattern.matches("^[0-9]+$", tempUci)) &&
 * main.customerService.checkUciList(new BigInteger(tempUci))) { do {
 * System.out.println("Password"); if (keyboardInput.readLine().length() > 0) {
 * System.out.print("Welcome"); System.out.print(" " +
 * main.customerService.returnName(new BigInteger(tempUci)));
 * System.out.print("\nYou are logged in successfully!!\n"); test = 0; } else {
 * test = 2; } } while (2 == test); uci = new BigInteger(tempUci); } else {
 * System.out.println("Customer ID doesn't exist"); test = 1; } } catch
 * (IOException | IBSExceptions exception) {
 * System.out.println(exception.getMessage()); } } while (1 == test);
 * 
 * }
 * 
 * 
 * private void customerAction() {// facilities provided to the IBS customer
 * CustomerUi choice = null; System.out.println(message1);
 * System.out.println("Choose the desired action");
 * System.out.println(message1); for (CustomerUi menu : CustomerUi.values()) {
 * System.out.println(menu.ordinal() + 1 + "\t" + menu); // showing options to
 * the customer } System.out.println(); int ordinal = choice(scanner) - 1;
 * 
 * if (0 <= ordinal && CustomerUi.values().length > ordinal) { choice =
 * CustomerUi.values()[ordinal]; switch (choice) { case CREDITCARD:
 * addOrDeleteCreditCard(); break; case BENEFICIARY: addOrModifyBeneficiary();
 * break; case AUTOPAYMENT: addOrRemoveAutopayments(); customerAction(); break;
 * case EXIT: System.out.println(message4); break; } } else {
 * System.out.println(message2); customerAction(); } }
 * 
 * private void addOrDeleteCreditCard() {
 * 
 * int count1 = 0; int count2 = 0; int count3 = 0;
 * 
 * try {
 * 
 * for (CreditCard card : main.cardService.showCardDetails(uci)) { if
 * (card.getCardStatus().equals(Status.PENDING)) { count1++; if (count1 == 1) {
 * System.out.println("\nYour Pending Credit Cards are:");
 * System.out.println(message5); System.out.printf("%20s %20s %20s",
 * "CREDIT_CARD_NUMBER", "NAME_ON_CARD", "EXPIRY_DATE" + "\n");
 * System.out.println(message5); } System.out.printf("%18s %18s %23s",
 * card.getCardNumber(), card.getNameOnCard(), card.getDateOfExpiry() + "\n");
 * System.out.println(message5); if (!card.getAdminRemarks().contentEquals(" "))
 * System.out.println("Bank Admin remarks:" + card.getAdminRemarks() + "\n" +
 * message1); } else continue; }
 * 
 * for (CreditCard card : main.cardService.showCardDetails(uci)) {
 * 
 * if (card.getCardStatus().equals(Status.ACTIVE)) { count2++; if (count2 == 1)
 * { System.out.println("\nYour Approved Credit Cards are:");
 * System.out.println(message5); System.out.printf("%20s %20s %20s",
 * "CREDIT_CARD_NUMBER", "NAME_ON_CARD", "EXPIRY_DATE" + "\n");
 * System.out.println(message5); } System.out.printf("%18s %18s %23s",
 * card.getCardNumber(), card.getNameOnCard(), card.getDateOfExpiry() + "\n");
 * System.out.println(message5); if (!card.getAdminRemarks().contentEquals(" "))
 * System.out.println("Bank Admin remarks:" + card.getAdminRemarks() + "\n" +
 * message1); } else continue; }
 * 
 * for (CreditCard card : main.cardService.showCardDetails(uci)) {
 * 
 * if (card.getCardStatus().equals(Status.BLOCKED)) { count3++; if (count3 == 1)
 * { System.out.println("\nYour Blocked Credit Cards are:");
 * System.out.println(message5); System.out.printf("%20s %20s %20s",
 * "CREDIT_CARD_NUMBER", "NAME_ON_CARD", "EXPIRY_DATE" + "\n");
 * System.out.println(message5); } System.out.printf("%18s %18s %23s",
 * card.getCardNumber(), card.getNameOnCard(), card.getDateOfExpiry() + "\n");
 * System.out.println(message5); if (!card.getAdminRemarks().contentEquals(" "))
 * System.out.println("Bank Admin remarks:" + card.getAdminRemarks() + "\n" +
 * message1); } else continue; } } catch (IBSExceptions exception) {
 * System.out.println("\nSorry " + main.customerService.returnName(uci));
 * System.out.println(exception.getMessage()); }
 * 
 * CreditCard card = new CreditCard(); int creditCardOption; do { System.out
 * .println("\nEnter 1 to add a credit card. \nEnter 2 to delete a credit card \nEnter 3 to go back."
 * ); creditCardOption = choice(scanner);// enter the credit card
 * 
 * switch (creditCardOption) { case 1: String cardNumber; String nameOnCard;
 * String tempExpiryDate; String expiryDate; try { do {
 * System.out.println("Please Enter a valid CreditCard number (16 digits)");
 * cardNumber = keyboardInput.readLine();
 * 
 * } while (!(main.cardService.validateCardNumber(cardNumber)));
 * 
 * System.out.println("Please enter the credit card number again"); String
 * cardNumberConfirm = keyboardInput.readLine(); while
 * (!(main.cardService.validateCardNumber(cardNumberConfirm) &&
 * cardNumber.equals(cardNumberConfirm))) { System.out.println(
 * "Both Credit Card numbers do not match.\nPlease enter the Credit Card Number again."
 * ); cardNumberConfirm = keyboardInput.readLine(); } BigInteger
 * creditCardNumber = new BigInteger(cardNumber);
 * card.setCardNumber(creditCardNumber);
 * 
 * do { System.out.println("Please enter a valid Name on your CreditCard ");
 * nameOnCard = keyboardInput.readLine();
 * 
 * } while (!(main.cardService.validateNameOnCard(nameOnCard)));
 * card.setNameOnCard(nameOnCard); do { System.out.
 * println("Please enter a valid expiry date on your card number in (MM/YYYY) format."
 * ); tempExpiryDate = keyboardInput.readLine(); StringBuffer tempDate = new
 * StringBuffer(); tempDate.append("30/"); tempDate.append(tempExpiryDate);
 * expiryDate = tempDate.toString();
 * 
 * } while (!(main.cardService.validateDateOfExpiry(expiryDate)));
 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 * LocalDate expiryDateNew = LocalDate.parse(expiryDate, formatter);
 * card.setDateOfExpiry(expiryDateNew); card.setTimestamp(LocalDateTime.now());
 * 
 * main.cardService.saveCardDetails(uci, card);
 * System.out.println("\nCard gone for approval.. Good luck!!");
 * System.out.println("\nWelcome back to the credit card facilities."); } catch
 * (IBSExceptions | IOException exception) {
 * System.out.println(exception.getMessage()); }
 * 
 * addOrDeleteCreditCard(); break; case 2: try { do {
 * System.out.println("Please Enter a valid CreditCard number (16 digits)");
 * cardNumber = keyboardInput.readLine();
 * 
 * } while (!(main.cardService.validateCardNumber(cardNumber)));
 * System.out.println("Please enter the credit card number again"); String
 * cardNumberConfirm = keyboardInput.readLine(); while
 * (!(main.cardService.validateCardNumber(cardNumberConfirm) &&
 * cardNumber.equals(cardNumberConfirm))) { System.out.println(
 * "Both Credit Card numbers do not match.\nPlease enter the Credit Card Number again."
 * ); cardNumberConfirm = keyboardInput.readLine(); } BigInteger
 * creditCardNumber = new BigInteger(cardNumber);
 * main.cardService.deleteCardDetails(creditCardNumber);
 * System.out.println("Card deleted!!"); System.out.println(message1);
 * System.out.println("\nWelcome back to the credit card facilities."); } catch
 * (IBSExceptions | IOException exception) {
 * System.out.println(exception.getMessage()); } addOrDeleteCreditCard();
 * 
 * break; case 3: customerAction(); break; default:
 * System.out.println("Please enter a valid choice"); creditCardOption = 0; } }
 * while (0 == creditCardOption);
 * 
 * }
 * 
 * private void addOrModifyBeneficiary() { // beneficiary related facilities
 * provided in this section int count1 = 0; int count2 = 0; int count3 = 0; int
 * beneficiaryOption;
 * 
 * try { for (Beneficiary beneficiary :
 * main.beneficiaryAccountService.showBeneficiaryAccount(uci)) {
 * 
 * if (beneficiary.getStatus().equals(Status.PENDING)) { count1++; if (count1 ==
 * 1) { System.out.println("\nYour Pending beneficairies are:");
 * System.out.println(message6); System.out.printf("%20s %20s %20s %20s %20s",
 * "BENEFICIARY_NAME", "ACCOUNT_NUMBER", "BANK_NAME", "IFSC_CODE",
 * "ACCOUNT_TYPE" + "\n"); System.out.println(message6); }
 * 
 * System.out.printf("%15s %22s %19s %25s %20s", beneficiary.getAccountName(),
 * beneficiary.getAccountNumber(), beneficiary.getBankName(),
 * beneficiary.getIfscCode(), beneficiary.getType() + "\n");
 * System.out.println(message6); if
 * (!beneficiary.getAdminRemarks().contentEquals(" "))
 * System.out.println("   Bank Admin remarks:" + beneficiary.getAdminRemarks() +
 * "\n" + message1); } else { continue; } }
 * 
 * for (Beneficiary beneficiary :
 * main.beneficiaryAccountService.showBeneficiaryAccount(uci)) {
 * 
 * if (beneficiary.getStatus().equals(Status.ACTIVE)) { count2++; if (count2 ==
 * 1) { System.out.println("\nYour Active beneficairies are:");
 * System.out.println(message6); System.out.printf("%20s %20s %20s %20s %20s",
 * "BENEFICIARY_NAME", "ACCOUNT_NUMBER", "BANK_NAME", "IFSC_CODE",
 * "ACCOUNT_TYPE" + "\n"); System.out.println(message6);
 * 
 * } System.out.printf("%15s %22s %19s %25s %21s", beneficiary.getAccountName(),
 * beneficiary.getAccountNumber(), beneficiary.getBankName(),
 * beneficiary.getIfscCode(), beneficiary.getType() + "\n");
 * System.out.println(message6); if
 * (!beneficiary.getAdminRemarks().contentEquals(" "))
 * System.out.println("   Bank Admin remarks:" + beneficiary.getAdminRemarks() +
 * "\n" + message1); } else { continue; } }
 * 
 * for (Beneficiary beneficiary :
 * main.beneficiaryAccountService.showBeneficiaryAccount(uci)) {
 * 
 * if (beneficiary.getStatus().equals(Status.BLOCKED)) { count3++; if (count3 ==
 * 1) { System.out.println("\nYour Active beneficairies are:");
 * System.out.println(message6); System.out.printf("%20s %20s %20s %20s %20s",
 * "BENEFICIARY_NAME", "ACCOUNT_NUMBER", "BANK_NAME", "IFSC_CODE",
 * "ACCOUNT_TYPE" + "\n"); System.out.println(message6);
 * 
 * } System.out.printf("%15s %22s %19s %25s %21s", beneficiary.getAccountName(),
 * beneficiary.getAccountNumber(), beneficiary.getBankName(),
 * beneficiary.getIfscCode(), beneficiary.getType() + "\n");
 * System.out.println(message6); if
 * (!beneficiary.getAdminRemarks().contentEquals(" "))
 * System.out.println("   Bank Admin remarks:" + beneficiary.getAdminRemarks() +
 * "\n" + message1); } else { continue; } } } catch (IBSExceptions exception) {
 * System.out.println("\nSorry " + main.customerService.returnName(uci));
 * System.out.println(exception.getMessage()); }
 * 
 * System.out.println(
 * "\nEnter 1 to add a beneficiary. \nEnter 2 to modify a beneficiary. \nEnter 3 to delete a beneficiary. \nEnter 4 to go back."
 * ); beneficiaryOption = choice(scanner);// To choose the facility. switch
 * (beneficiaryOption) { case 1: Type choice = null;
 * System.out.println(message1);
 * System.out.println("Choose the desired type of account");
 * System.out.println(message1); for (Type menu : Type.values()) {
 * System.out.println(menu.ordinal() + 1 + "\t" + menu); }
 * System.out.println(message3); int ordinal = choice(scanner) - 1;
 * 
 * if (0 <= ordinal && CustomerUi.values().length > ordinal) { choice =
 * Type.values()[ordinal]; switch (choice) { case MYACCOUNTINIBS:
 * addBeneficiary(choice); break; case MYACCOUNTINOTHERBANKS:
 * addBeneficiary(choice); break; case OTHERSACCOUNTINOTHERBANKS:
 * addBeneficiary(choice); break; case OTHERSACCOUNTINIBS:
 * addBeneficiary(choice); break; } } else { System.out.println(message2);
 * 
 * } addOrModifyBeneficiary(); break; case 2: String accountNumber1; BigInteger
 * accountNumber = null; if (0 != count2) { int count5; int choiceToModify; do {
 * count5 = 0; try {
 * 
 * do { System.out.println("Please enter a valid Account number(11 digits)");
 * accountNumber1 = keyboardInput.readLine(); } while
 * (!main.beneficiaryAccountService.validateBeneficiaryAccountNumber(
 * accountNumber1));
 * 
 * System.out.println("Please enter the account number again"); String
 * accountNumberConfirm = keyboardInput.readLine(); while
 * (!main.beneficiaryAccountService.validateBeneficiaryAccountNumber(
 * accountNumberConfirm) && accountNumber1.equals(accountNumberConfirm)) {
 * System.out.println(
 * "Both Account numbers do not match.\nPlease enter the Account Number again."
 * ); accountNumberConfirm = keyboardInput.readLine(); } accountNumber = new
 * BigInteger(accountNumber1); for (Beneficiary beneficiary :
 * main.beneficiaryAccountService.showBeneficiaryAccount(uci)) { if
 * (beneficiary.getAccountNumber().equals(accountNumber)) { count5++; } } }
 * catch (IOException | IBSExceptions exception) {
 * System.out.println(exception.getMessage()); } } while (count5 == 0);
 * Beneficiary beneficiary = new Beneficiary(); int test = 0; do {
 * System.out.println(
 * "\nEnter 1 to change the Account Holder Name. \nEnter 2 to change the IFSC code. \nEnter 3 to change the bank name."
 * +
 * "\nEnter 4 to save changes. (Once saved changes, you can't change again till bank verification is done) \nEnter 5 to exit."
 * ); choiceToModify = choice(scanner); switch (choiceToModify) { case 1: String
 * nameInAccount; try { do {
 * System.out.println("Enter a valid account holder name"); nameInAccount =
 * keyboardInput.readLine(); } while (!main.beneficiaryAccountService
 * .validateBeneficiaryAccountNameOrBankName(nameInAccount));
 * beneficiary.setAccountName(nameInAccount); test = 0; } catch (IOException
 * exception) { System.out.println(exception.getMessage()); } break; case 2:
 * String ifscNewValue; try { Beneficiary beneficiary2 =
 * main.beneficiaryAccountService.getBeneficiary(accountNumber); if
 * (beneficiary2.getType() == Type.MYACCOUNTINOTHERBANKS ||
 * beneficiary2.getType() == Type.OTHERSACCOUNTINOTHERBANKS) { do {
 * System.out.println("Enter a valid IFSC code(11 characters)"); ifscNewValue =
 * keyboardInput.readLine(); } while
 * (!main.beneficiaryAccountService.validateBeneficiaryIfscCode(ifscNewValue));
 * beneficiary.setIfscCode(ifscNewValue); test = 0; } else {
 * System.out.println("Modification in ifsc not allowed"); } } catch
 * (IOException | IBSExceptions exception) {
 * System.out.println(exception.getMessage()); } break; case 3: String
 * bankNameNewValue; try { Beneficiary beneficiary2 =
 * main.beneficiaryAccountService.getBeneficiary(accountNumber); if
 * (beneficiary2.getType() == Type.MYACCOUNTINOTHERBANKS ||
 * beneficiary2.getType() == Type.OTHERSACCOUNTINOTHERBANKS) { do {
 * System.out.println("Enter a new valid bank name"); bankNameNewValue =
 * keyboardInput.readLine(); } while (!main.beneficiaryAccountService
 * .validateBeneficiaryAccountNameOrBankName(bankNameNewValue));
 * beneficiary.setBankName(bankNameNewValue); test = 0; } else {
 * System.out.println("Modification not allowed"); } } catch (IOException |
 * IBSExceptions exception) { System.out.println(exception.getMessage()); }
 * break; case 4: try { beneficiary.setTimestamp(LocalDateTime.now());
 * main.beneficiaryAccountService.modifyBeneficiaryAccountDetails(accountNumber,
 * beneficiary);
 * System.out.println("\nModified beneficiary details are gone for approval.");
 * } catch (IBSExceptions | IOException exception) {
 * System.out.println(exception.getMessage()); } test = 1; break; case 5:
 * addOrModifyBeneficiary(); test = 1; break; default:
 * System.out.println("Wrong Input"); test = 0; break; } } while (0 == test); }
 * else { System.out.println("\nNo beneficiary accounts to modify."); }
 * addOrModifyBeneficiary(); break; case 3: String deleteAccountNum = null; if
 * (0 != count2) { try { do {
 * System.out.println("Please enter a valid Account number(11 digits)");
 * deleteAccountNum = keyboardInput.readLine(); } while
 * (!main.beneficiaryAccountService.validateBeneficiaryAccountNumber(
 * deleteAccountNum));
 * 
 * System.out.println("Please enter the account number again"); String
 * accountNumberConfirm = keyboardInput.readLine(); while
 * (!(main.beneficiaryAccountService.validateBeneficiaryAccountNumber(
 * accountNumberConfirm) && deleteAccountNum.equals(accountNumberConfirm))) {
 * System.out
 * .println("Both Account numbers do not match.\nPlease enter the Account Number again."
 * ); accountNumberConfirm = keyboardInput.readLine(); }
 * main.beneficiaryAccountService.deleteBeneficiaryAccountDetails(new
 * BigInteger(deleteAccountNum));
 * System.out.println("Account deleted Successfully"); } catch (IOException |
 * IBSExceptions exception) { System.out.println(exception.getMessage()); } }
 * else { System.out.println("\nYou need to add beneficiaries first...!"); }
 * addOrModifyBeneficiary(); break; case 4: customerAction(); break; default:
 * System.out.println("Please enter a valid choice"); addOrModifyBeneficiary();
 * } }
 * 
 * private void addBeneficiary(Type type) { Beneficiary beneficiary = new
 * Beneficiary(); boolean valid = false; String accountNumber1; String
 * nameInAccount; String ifsc; String bankName; try { do {
 * System.out.println("Please enter a valid Account number(11 digits)");
 * accountNumber1 = keyboardInput.readLine(); } while
 * (!main.beneficiaryAccountService.validateBeneficiaryAccountNumber(
 * accountNumber1));
 * 
 * System.out.println("Please enter the account number again"); String
 * accountNumberConfirm = keyboardInput.readLine(); while
 * (!(main.beneficiaryAccountService.validateBeneficiaryAccountNumber(
 * accountNumberConfirm) && accountNumber1.equals(accountNumberConfirm))) {
 * System.out.
 * println("Both Account numbers do not match.\nPlease enter the Account Number again."
 * ); accountNumberConfirm = keyboardInput.readLine(); } BigInteger
 * accountNumber = new BigInteger(accountNumber1);
 * beneficiary.setAccountNumber(accountNumber); do {
 * System.out.println("Please enter a valid Account Holder Name ");
 * nameInAccount = keyboardInput.readLine(); } while
 * (!main.beneficiaryAccountService.validateBeneficiaryAccountNameOrBankName(
 * nameInAccount)); beneficiary.setAccountName(nameInAccount); if (type ==
 * Type.MYACCOUNTINOTHERBANKS || type == Type.OTHERSACCOUNTINOTHERBANKS) { do {
 * System.out.println("Please enter a valid IFSC code(11 characters)"); ifsc =
 * keyboardInput.readLine(); valid =
 * main.beneficiaryAccountService.validateBeneficiaryIfscCode(ifsc); } while
 * (!valid);
 * 
 * beneficiary.setIfscCode(ifsc); } else {
 * beneficiary.setIfscCode("IBS12312312"); } if (type ==
 * Type.MYACCOUNTINOTHERBANKS || type == Type.OTHERSACCOUNTINOTHERBANKS) { do {
 * System.out.println("Enter the bank name (case sensitive)"); bankName =
 * keyboardInput.readLine(); } while
 * (!main.beneficiaryAccountService.validateBeneficiaryAccountNameOrBankName(
 * bankName)); beneficiary.setBankName(bankName); } else {
 * beneficiary.setBankName("IBS"); } } catch (IOException exception) {
 * System.out.println(exception.getMessage()); } beneficiary.setType(type);
 * beneficiary.setTimestamp(LocalDateTime.now());
 * 
 * try { main.beneficiaryAccountService.saveBeneficiaryAccountDetails(uci,
 * beneficiary); System.out.println("\nThe details entered by you are : " +
 * "\nName of the beneficiary : " + beneficiary.getAccountName() +
 * "\nBeneficiary account number : " + beneficiary.getAccountNumber() +
 * "\nBank name : " + beneficiary.getBankName() + "\nIFSC code : " +
 * beneficiary.getIfscCode());
 * System.out.println("Beneficiary gone for approval... Good luck!!"); } catch
 * (IBSExceptions exception) { System.out.println(exception.getMessage()); } }
 * 
 * private void addOrRemoveAutopayments() { int count2 = 0; try { for
 * (AutoPayment autoPayment1 :
 * main.autopaymentservice.showAutopaymentDetails(uci)) { count2++; if (1 ==
 * count2) {
 * System.out.println("\nThe added autopayment services for the customer.");
 * System.out.println(message7); System.out.printf("%20s %20s %20s %20s",
 * "SERVICE_NAME", "START_DATE", "END_DATE", "AMOUNT" + "\n");
 * System.out.println(message7); }
 * 
 * System.out.printf("%16s %24s %22s %16s", autoPayment1.getServiceName(),
 * autoPayment1.getDateOfStart(), autoPayment1.getDateOfEnd(),
 * autoPayment1.getAmount() + "\n"); System.out.println(message7); count2++; } }
 * catch (IBSExceptions exception) { System.out.println("\nSorry " +
 * main.customerService.returnName(uci));
 * System.out.println(exception.getMessage()); } AutoPaymentUi choice = null;
 * System.out.println("\n\n" + message1);
 * System.out.println("Choose a valid option"); System.out.println(message1);
 * for (AutoPaymentUi menu : AutoPaymentUi.values()) {
 * System.out.println(menu.ordinal() + 1 + "\t" + menu); }
 * System.out.println(message3); int ordinal = choice(scanner) - 1;
 * 
 * if (0 <= ordinal && AutoPaymentUi.values().length > ordinal) { choice =
 * AutoPaymentUi.values()[ordinal]; switch (choice) { case ADDAUTOPAYMENTS:
 * addAutoPayment(); // this method is for adding auto payment // details
 * addOrRemoveAutopayments();// this method is for showing auto // payment menu
 * again break; case UPDATEAUTOPAYMENTS: updateAutoPayment();
 * 
 * break; case REMOVEAUTOPAYMENTS: removeAutoPayment();
 * addOrRemoveAutopayments(); break; case EXIT: System.out.println(message4);
 * break; default: System.out.println("Enter a valid choice");
 * addOrRemoveAutopayments(); } } else { System.out.println(message2);
 * addOrRemoveAutopayments(); } }
 * 
 * private void addAutoPayment() { int count = 1; String tempInput = null;
 * boolean valid13 = true; AutoPayment autoPayment = new AutoPayment();
 * Set<ServiceProvider> serviceProviders; serviceProviders =
 * main.autopaymentservice.showIBSServiceProviders();
 * System.out.println("\nYour IBS accounts are : ");
 * System.out.println(message1); System.out.printf("%3s %15s", "SNo.",
 * "ACCOUNTS" + "\n"); System.out.println(message1);
 * 
 * try { int count1 =1; for (AccountBean accountBean :
 * main.accountService.getAccountsOfUci(uci)) { System.out.printf("%3s %18s",
 * count1, accountBean.getAccNo() + "\n"); count1++; }
 * System.out.println(message1); } catch (IBSExceptions exception) {
 * System.out.println(exception.getMessage()); count++; } if (count == 1) {
 * System.out.println("\nThe IBS service providers are : ");
 * System.out.println(message1); System.out.printf("%17s", "SERVICE_LIST" +
 * "\n"); System.out.println(message1); for (ServiceProvider serviceProvider :
 * serviceProviders) { System.out.printf("%12s",
 * serviceProvider.getNameOfCompany() + "\n"); } System.out.println(message1);
 * 
 * while (valid13) {
 * System.out.println("\nEnter a valid service name to be registerd."); try {
 * tempInput = keyboardInput.readLine(); for (ServiceProvider serviceProvider :
 * serviceProviders) { if (serviceProvider.getNameOfCompany().equals(tempInput))
 * { autoPayment.setServiceName(tempInput); autoPayment.setServiceProviderId(new
 * ServiceProviderId(serviceProvider.getSpi(), uci)); valid13 = false; } } }
 * catch (IOException e) { System.out.println(e.getMessage()); } }
 * 
 * BigInteger accountNumber = null; boolean valid15 = false; do { System.out.
 * println("Enter a valid account number from which amount should be deducted");
 * try { tempInput = keyboardInput.readLine(); if
 * (Pattern.matches("^[0-9]{11}$", tempInput)) { accountNumber = new
 * BigInteger(tempInput); valid15 = true; } } catch (IOException e) {
 * System.out.println(e.getMessage()); } } while (!valid15);
 * 
 * BigDecimal amount = null; boolean valid14 = false; do {
 * System.out.println("Enter a valid amount to be deducted"); try { tempInput =
 * keyboardInput.readLine(); if (Pattern.matches("^[0-9]+$", tempInput)) {
 * amount = new BigDecimal(tempInput); valid14 = true; } } catch (IOException e)
 * { System.out.println(e.getMessage()); } } while (!valid14);
 * autoPayment.setAmount(amount);
 * 
 * String mydate = null; do {
 * System.out.println("Enter a valid start date in format dd/MM/yyyy"); try {
 * mydate = keyboardInput.readLine(); } catch (IOException exception) {
 * System.out.println(exception.getMessage()); } } while
 * (!main.cardService.validateDateOfExpiry(mydate));
 * 
 * String endDate = null; do {
 * System.out.println("Enter a valid end date in format dd/MM/yyyy"); try {
 * endDate = keyboardInput.readLine(); } catch (IOException exception) {
 * System.out.println(exception.getMessage()); } } while
 * (!main.autopaymentservice.validEndDate(endDate, mydate));
 * autoPayment.setDateOfStart(mydate); autoPayment.setDateOfEnd(endDate); try {
 * main.autopaymentservice.autoDeduction(uci, accountNumber, autoPayment);
 * System.out.println("\nAutoPayment of service provider: " + tempInput +
 * " added and Rs. " + amount +
 * " will be deducted per month from the date of start till end date." +
 * endDate); } catch (IBSExceptions exception) {
 * System.out.println(exception.getMessage()); }
 * 
 * } else { System.out.
 * println("Auto Payment can not be availed without an existing IBS account"); }
 * }
 * 
 * private void updateAutoPayment() { String spName = null; Set<ServiceProvider>
 * serviceProviders; BigInteger spId = null;
 * System.out.println("Please enter the service provider name registered"); try
 * { spName = keyboardInput.readLine(); } catch (IOException exception) {
 * System.out.println(exception.getMessage()); } serviceProviders =
 * main.autopaymentservice.showIBSServiceProviders(); for (ServiceProvider
 * serviceProvider : serviceProviders) { if
 * (serviceProvider.getNameOfCompany().equals(spName)) { spId =
 * serviceProvider.getSpi(); } } AutoPayment autoPayment = new AutoPayment();
 * int test = 0; do { System.out.println(
 * "\nEnter 1 to change the Amount. \nEnter 2 to change the start date. \nEnter 3 to change the end date."
 * + "\nEnter 4 to save changes. \nEnter 5 to exit."); int choiceToModify =
 * choice(scanner); switch (choiceToModify) { case 1: String amount; BigDecimal
 * amount1 = null; try { int choice = 0; do {
 * System.out.println("Please enter a valid amount"); amount =
 * keyboardInput.readLine(); if (Pattern.matches("^[0-9]+$", amount)) { choice =
 * 1; amount1 = new BigDecimal(amount); autoPayment.setAmount(amount1); } }
 * while (choice == 0); } catch (IOException exception) {
 * System.out.println(exception.getMessage()); } test = 0; break; case 2: String
 * startDate = null; try { do { System.out.println("Enter a valid start date");
 * startDate = keyboardInput.readLine(); } while
 * (!main.cardService.validateDateOfExpiry(startDate));
 * autoPayment.setDateOfStart(startDate); } catch (IOException exception) {
 * System.out.println(exception.getMessage()); } test = 0; break; case 3:
 * AutoPayment autoPayment2; String endDate = null; try { autoPayment2 =
 * autopaymentservice.getAutopayment(new ServiceProviderId(spId, uci)); do {
 * System.out.println("Enter a valid end date"); endDate =
 * keyboardInput.readLine(); } while
 * (!main.autopaymentservice.validEndDate(endDate,
 * autoPayment2.getDateOfStart())); autoPayment.setDateOfEnd(endDate); } catch
 * (IOException | IBSExceptions exception) {
 * System.out.println(exception.getMessage()); } test = 0; break; case 4: try {
 * main.autopaymentservice.updateDetails(new ServiceProviderId(spId, uci),
 * autoPayment); System.out.println("\nModified auto payment successfully..!");
 * } catch (IBSExceptions exception) {
 * System.out.println(exception.getMessage()); } test = 1; break; case 5:
 * addOrRemoveAutopayments(); test = 1; break; default:
 * System.out.println("Wrong Input"); test = 0; break; } } while (0 == test); }
 * 
 * private void removeAutoPayment() {// removal of autopayment
 * 
 * String spName = null; Set<ServiceProvider> serviceProviders; BigInteger spId
 * = null;
 * System.out.println("Please enter the service provider name registered"); try
 * { spName = keyboardInput.readLine(); } catch (IOException exception) {
 * System.out.println(exception.getMessage()); } serviceProviders =
 * main.autopaymentservice.showIBSServiceProviders(); for (ServiceProvider
 * serviceProvider : serviceProviders) { if
 * (serviceProvider.getNameOfCompany().equals(spName)) { spId =
 * serviceProvider.getSpi(); } }
 * 
 * try { main.autopaymentservice.deleteAutopayment(uci, spId);
 * System.out.println("Autopayment service removed successfully"); } catch
 * (IBSExceptions exception) { System.out.println(exception.getMessage()); } }
 * 
 * private void bankRepresentativeAction() {// user interface for bank //
 * representative Bank_Admin choice = null; System.out.println(message1);
 * System.out.println("Choose a valid option"); System.out.println(message1);
 * for (Bank_Admin menu : Bank_Admin.values()) {
 * System.out.println(menu.ordinal() + 1 + "\t" + menu); }
 * System.out.println(message3); int ordinal = choice(scanner) - 1; if (0 <=
 * ordinal && Bank_Admin.values().length > ordinal) { choice =
 * Bank_Admin.values()[ordinal]; switch (choice) { case VIEWREQUESTS:
 * showRequests(); bankRepresentativeAction(); break; case EXIT:
 * System.out.println(message4); break; } } else { System.out.println(message2);
 * bankRepresentativeAction(); } }
 * 
 * private void showRequests() { Set<BigInteger> customerRequests = null; String
 * uci11 = null; int count=1; BigInteger uci1 = null; try { customerRequests =
 * main.bankRepresentativeService.showRequests();
 * 
 * if (customerRequests.isEmpty()) { System.out.println("No new requests."); }
 * else {
 * System.out.println("The following customers have new approval requests :");
 * System.out.println(message1 + "---------");
 * System.out.printf("%5s %18s","SNo." ,"REQUESTS" + "\n");
 * System.out.println(message1+ "---------"); for (BigInteger customerRequest :
 * customerRequests) { System.out.printf("%3s %25s", count ,customerRequest +
 * "\n"); count++; } System.out.println(message1 + "---------"); } int choice =
 * 0; do { System.out.
 * println("Please enter a valid customer id to view individual requests \nEnter 1 to exit"
 * ); uci11 = keyboardInput.readLine(); if (Pattern.matches("^[0-9]+$", uci11))
 * { choice = 1; uci1 = new BigInteger(uci11); } } while (choice == 0); } catch
 * (IOException exception) { System.out.println(exception.getMessage()); }
 * 
 * if (!uci11.equals("1")) { if (customerRequests.contains(uci1)) {
 * System.out.println("Id entered by you is : " + uci1); int choice7 = 0; do {
 * int choice1; if
 * (!main.bankRepresentativeService.showUnapprovedCreditCards(uci1).isEmpty()) {
 * System.out.println("\nCredit Card requests are:");
 * System.out.println(message5); System.out.printf("%20s %20s %20s %20s",
 * "CREDIT_CARD_NUMBER", "NAME_ON_CARD", "EXPIRY_DATE", "TIME_STAMP" + "\n");
 * System.out.println(message5); for (CreditCard creditCard :
 * main.bankRepresentativeService.showUnapprovedCreditCards(uci1)) {
 * 
 * System.out.printf("%18s %18s %23s %33s", creditCard.getCardNumber(),
 * creditCard.getNameOnCard(), creditCard.getDateOfExpiry(),
 * creditCard.getTimestamp() + "\n"); System.out.println(message5);
 * 
 * } } else { System.out.println("\n\nNo credit card requests."); } if
 * (!main.bankRepresentativeService.showUnapprovedBeneficiaries(uci1).isEmpty())
 * { System.out.println("\nBeneficiary requests are:");
 * System.out.println(message6);
 * System.out.printf("%20s %20s %20s %20s %20s %20s", "BENEFICIARY_NAME",
 * "ACCOUNT_NUMBER", "BANK_NAME", "IFSC_CODE", "ACCOUNT_TYPE", "TIME_STAMP" +
 * "\n"); System.out.println(message6);
 * 
 * for (Beneficiary beneficiary : main.bankRepresentativeService
 * .showUnapprovedBeneficiaries(uci1)) {
 * 
 * System.out.printf("%15s %22s %19s %25s %20s %33s",
 * beneficiary.getAccountName(), beneficiary.getAccountNumber(),
 * beneficiary.getBankName(), beneficiary.getIfscCode(), beneficiary.getType(),
 * beneficiary.getTimestamp() + "\n"); System.out.println(message6); } } else {
 * System.out.println("\n\nNo beneficiary requests."); } System.out.println(
 * "\nEnter 1 to view Creditcard requests. \nEnter 2 to view Beneficiary Requests. \nEnter 3 to exit."
 * ); choice1 = choice(scanner); switch (choice1) { case 1: Iterator<CreditCard>
 * itCredit = null; itCredit =
 * main.bankRepresentativeService.showUnapprovedCreditCards(uci1).iterator(); if
 * (!(itCredit.hasNext())) { System.out.println("No more credit card requests");
 * break; } int option = 0;
 * 
 * while (itCredit.hasNext() && 0 == option) { CreditCard creditCard =
 * itCredit.next(); System.out.println(message5);
 * System.out.printf("%20s %20s %20s %20s", "CREDIT_CARD_NUMBER",
 * "NAME_ON_CARD", "EXPIRY_DATE", "TIME_STAMP" + "\n");
 * System.out.println(message5); System.out.printf("%18s %18s %23s %33s",
 * creditCard.getCardNumber(), creditCard.getNameOnCard(),
 * creditCard.getDateOfExpiry(), creditCard.getTimestamp() + "\n");
 * System.out.println(message5); int choice2; do { System.out.println(
 * "\nPress 1 or 2 according to the decision and proceed to the next card." +
 * "\nEnter 1 to approve. \nEnter 2 to disapprove. \nEnter 3 to exit this section."
 * ); choice2 = choice(scanner);
 * 
 * switch (choice2) { case 1: try { System.out.println(
 * "Would you like to enter remarks?\nEnter 1 for yes and 2 for no."); int
 * response = choice(scanner); if (response == 1) { try {
 * System.out.println("Please enter the response.");
 * creditCard.setAdminRemarks(keyboardInput.readLine()); } catch (IOException e)
 * { System.out.println(e.getMessage()); } } else
 * creditCard.setAdminRemarks(" "); main.bankRepresentativeService
 * .saveCreditCardDetails(creditCard.getCardNumber());
 * System.out.println(message1 + "\nCard approved by the bank representative.");
 * if (!creditCard.getAdminRemarks().contentEquals(" ")) {
 * System.out.println("Bank admin response:");
 * System.out.println(creditCard.getAdminRemarks() + "\n"); } } catch
 * (IBSExceptions exception) { exception.getMessage(); } option = 0; break; case
 * 2: try { System.out.println(
 * "Would you like to enter remarks?\nEnter 1 for yes and 2 for no."); int
 * response = choice(scanner); if (response == 1) { try {
 * System.out.println("Please enter the response.");
 * creditCard.setAdminRemarks(keyboardInput.readLine()); } catch (IOException e)
 * { System.out.println(e.getMessage()); } } else
 * creditCard.setAdminRemarks(" ");
 * main.bankRepresentativeService.disapproveCreditCard(creditCard.getCardNumber(
 * )); System.out.println("Card disapproved by the bank representative."); if
 * (!creditCard.getAdminRemarks().contentEquals(" ")) {
 * System.out.println("Bank admin response:");
 * System.out.println(creditCard.getAdminRemarks() + "\n"); } } catch
 * (IBSExceptions exception) { exception.getMessage(); } option = 0; break; case
 * 3: option = 1; break; default:
 * System.out.println("Enter a valid choice of decision of credit card");
 * choice2 = 0; } } while (0 == choice2); } choice7 = 1; break; case 2:
 * Iterator<Beneficiary> itBeneficiary = null; // showing beneficiary requests
 * to bank representative itBeneficiary =
 * main.bankRepresentativeService.showUnapprovedBeneficiaries(uci1).iterator();
 * if (!(itBeneficiary.hasNext())) {
 * System.out.println("No more beneficiary requests"); break; } int option1 = 0;
 * 
 * while (itBeneficiary.hasNext() && 0 == option1) { Beneficiary beneficiary =
 * itBeneficiary.next(); System.out.println(message6);
 * System.out.printf("%20s %20s %20s %20s %20s %20s", "BENEFICIARY_NAME",
 * "ACCOUNT_NUMBER", "BANK_NAME", "IFSC_CODE", "ACCOUNT_TYPE", "TIME_STAMP" +
 * "\n"); System.out.println(message6);
 * System.out.printf("%15s %22s %19s %25s %20s %33s",
 * beneficiary.getAccountName(), beneficiary.getAccountNumber(),
 * beneficiary.getBankName(), beneficiary.getIfscCode(), beneficiary.getType(),
 * beneficiary.getTimestamp() + "\n"); System.out.println(message6); int
 * choice2; do { System.out.println(
 * "\nPress 1 or 2 according to the decision and proceed to the next beneficiary."
 * +
 * "\nEnter 1 to approve. \nEnter 2 to disapprove. \nEnter 3 to exit this section."
 * ); choice2 = choice(scanner);
 * 
 * switch (choice2) { case 1: try { System.out.println(
 * "Would you like to enter remarks?\nEnter 1 for yes and 2 for no."); int
 * response = choice(scanner); if (response == 1) { try {
 * System.out.println("Please enter the response.");
 * beneficiary.setAdminRemarks(keyboardInput.readLine()); } catch (IOException
 * e) { System.out.println(e.getMessage()); } } else {
 * beneficiary.setAdminRemarks(" "); } main.bankRepresentativeService
 * .saveBeneficiaryDetails(beneficiary.getAccountNumber());
 * System.out.println("Beneficiary approved by the bank representative."); if
 * (!beneficiary.getAdminRemarks().contentEquals(" ")) {
 * System.out.println("Bank admin response:");
 * System.out.println(beneficiary.getAdminRemarks() + "\n"); }
 * 
 * } catch (IBSExceptions exception) {
 * System.out.println(exception.getMessage()); } option1 = 0; break; case 2: try
 * { System.out.println(
 * "Would you like to enter remarks?\nEnter 1 for yes and 2 for no."); int
 * response = choice(scanner); if (response == 1) { try {
 * System.out.println("Please enter the response.");
 * beneficiary.setAdminRemarks(keyboardInput.readLine()); } catch (IOException
 * e) { System.out.println(e.getMessage()); } } main.bankRepresentativeService
 * .disapproveBenficiary(beneficiary.getAccountNumber());
 * System.out.println("Beneficiary disapproved  by the bank representative.");
 * if (!beneficiary.getAdminRemarks().contentEquals(" ")) {
 * System.out.println("Bank admin response:");
 * System.out.println(beneficiary.getAdminRemarks() + "\n"); } else
 * beneficiary.setAdminRemarks(" ");
 * 
 * } catch (IBSExceptions e) { System.out.println(e.getMessage()); } option1 =
 * 0; break; case 3: option1 = 1; break; default:
 * System.out.println("Enter a valid choice of decision of beneficiary");
 * choice2 = 0; } } while (0 == choice2); } choice7 = 1; break; case 3: choice7
 * = 0; break; default: System.out.println("Enter a valid choice of action"); }
 * } while (1 == choice7);
 * 
 * } else { System.out.println("\nInvalid customer ID"); showRequests(); } }
 * 
 * }
 * 
 * private int choice(Scanner scanner1) { while (!scanner1.hasNextInt()) {
 * scanner1.next(); scanner1.nextLine();
 * System.out.println("Please enter a valid input"); } int chosenOption =
 * scanner1.nextInt(); scanner1.nextLine(); return chosenOption; }
 * 
 * public static void main(String[] args) { scanner = new Scanner(System.in);
 * context = new ClassPathXmlApplicationContext("applicationContext.xml"); main
 * = context.getBean(MainUi.class); main.start(); scanner.close();
 * System.out.println(); } }
 */