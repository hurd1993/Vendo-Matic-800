package com.techelevator;

import com.techelevator.products.VendingMachineSlot;
import com.techelevator.view.Menu;


public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] MAIN_MENU_OPTIONS = {
            MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};
    private static final String[] PURCHASE_MENU_OPTIONS =
            {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
    private static final Inventory inventory = new Inventory();


    private final Menu menu;


    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {
        inventory.stockInventory("vendingmachine.csv");
        SalesReport salesReport = new SalesReport(inventory, "SalesLog");
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            switch (choice) {
                case MAIN_MENU_OPTION_DISPLAY_ITEMS:
                    //Display Vending Machine Items
                    inventory.displayVendingMachineItems();

                    break;
                case MAIN_MENU_OPTION_PURCHASE:
                    // do purchase
                    Funds currentFunds = new Funds();
                    while (true) {

                        //System.out.println("Current Money Provided: " + currentFunds);
                        String message = "Current Money Provided: " + currentFunds;
                        String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS, message);

                        if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                            //Customer feeds valid bills to machine ($1,$2,$5, or $10)

                            while (true) {
                                System.out.println("\nCurrent Money Provided: " + currentFunds);
                                System.out.print("Please Feed appropriate Bill(Press 'E' to Exit)>>> ");
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
                            //Customer is displayed the products again and given the option to purchase
                            inventory.displayVendingMachineItems();
                            System.out.println("\nCurrent Money Provided: " + currentFunds);
                            System.out.print("Enter Item Slot for Purchase>>> ");
                            String slotChoice = menu.getIn().nextLine();
                            if (inventory.isValidSlot(slotChoice)) {
                                //Private Helper Method to store the logic for handling purchases
                                purchaseItemInSlot(salesReport, currentFunds, slotChoice);
                            } else {
                                System.out.println("***Invalid Slot Choice***");
                            }


                        } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                            //Customer Receives their appropriate change back.
                            System.out.println("Thank you for you purchase(s)! " + currentFunds.getChangeBack());
                            break;
                        }
                    }
                    break;
                case MAIN_MENU_OPTION_SALES_REPORT:
                    //Write Updated Sales Report
                    salesReport.writeReportToFile();
                    break;
                case MAIN_MENU_OPTION_EXIT:
                    //Write updated Sales Report before leaving so program has most up-to-date version on restart.
                    salesReport.writeReportToFile();
                    return;
            }
        }
    }

    private void purchaseItemInSlot(SalesReport salesReport, Funds currentFunds, String slotChoice) {
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
    }


    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
