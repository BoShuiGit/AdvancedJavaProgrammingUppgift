

import java.util.Date;

public abstract class BasTransaction {

    private final Date date;
    private final Double amount;

    public BasTransaction (Date date, Double amount){
        this.date = date;
        this.amount = amount;
    }

    public Date getDate(){
        return date;
    }
    public Double getAmount(){
        return amount;
    }

    public abstract String getType();
}

class IncomeTransaction extends BasTransaction {
    public IncomeTransaction(Date date, Double amount){
    super(date, amount);
    }

    @Override
    public String getType() {
        return "Income";  
    } 
    
}

class ExpenseTransaction extends BasTransaction {

    public ExpenseTransaction (Date date, Double amount){
        super(date, amount);
    }

    @Override
    public String getType(){
        return "Expense";
    }
}

