package com.techelevator;

import com.techelevator.products.VendingMachineSlot;
import com.techelevator.view.Menu;

import java.io.File;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT,MAIN_MENU_OPTION_SALES_REPORT};
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
    private static final Inventory inventory = new Inventory();
    private static SalesReport salesReport;


    private Menu menu;


    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {
        inventory.stockInventory("vendingmachine.csv");
        salesReport = new SalesReport(inventory, "SalesLog");
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                //Display Vending Machine Items
                inventory.displayVendingMachineItems();

            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                // do purchase
                //double currentMoneyProvided = 0;
                Funds currentFunds = new Funds();
                while (true) {

                    System.out.println("Current Money Provided: " + currentFunds);
                    String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

                    if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                        //Customer feeds valid bills to machine ($1,$2,$5, or $10)

                        while (true) {
                            System.out.println("Current Money Provided: " + currentFunds);
                            System.out.print("Please Feed appropriate Bill(Press 'E' to Exit): " + "\n");
                            String bill = menu.getIn().nextLine();
                            try {
                                if (bill.equalsIgnoreCase("E")) {
                                    break;
                                } else {
                                    currentFunds.feedFunds(bill);
                                }
                            } catch (UserInputException e) {
                                System.out.println(e.getMessage());
                            } catch (NumberFormatException e) {
                                System.out.println("Please Enter 'E' to Exit");
                            }
                        }
                    } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {

                        inventory.displayVendingMachineItems();
                        System.out.println("Current Money Provided: " + currentFunds);
                        System.out.println("Enter Item Slot for Purchase: ");
                        String slotChoice = menu.getIn().nextLine();
                        if (inventory.isValidSlot(slotChoice)) {
                            for (VendingMachineSlot vendingSlot : inventory.getVendingSlots()) {
                                if (vendingSlot.getSlotIdentifier().equalsIgnoreCase(slotChoice)) {
                                    if (vendingSlot.getCurrentQuantity() > 0) {
                                        try {
                                            String ogFunds = currentFunds.toString();
                                            currentFunds.removeFunds(vendingSlot.getAssignedItem().getPrice());
                                            AuditLog.log(vendingSlot.getAssignedItem().getName() + " " + vendingSlot.getSlotIdentifier()
                                                    + " " + ogFunds + " " + currentFunds);

                                            vendingSlot.reduceQuantity();
                                            salesReport.updateSalesReport(vendingSlot.getAssignedItem().getName());
                                            System.out.printf("Item: %s| Cost: %s| Money Remaining: %s|\n",
                                                    vendingSlot.getAssignedItem().getName(), vendingSlot.getAssignedItem().getPriceAsString(), currentFunds);
                                            System.out.println(vendingSlot.getAssignedItem().toString());
                                        } catch (UserInputException e) {
                                            System.out.println(e.getMessage());
                                        }


                                    } else {
                                        System.out.println("***" + vendingSlot.getAssignedItem().getName() + " is Sold Out***");
                                    }
                                }

                            }
                        } else {
                            System.out.println("***Invalid Slot Choice***");
                        }


                    } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                        System.out.println("Thank you for you purchase(s)! " + currentFunds.getChangeBack());
                        break;
                    }
                }
            } else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
                salesReport.writeReportToFile();
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                salesReport.writeReportToFile();
                return;
            }
        }
    }


    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
