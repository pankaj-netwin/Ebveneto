package com.ebveneto.models;

/**
 * Created by Mayur on 26-10-2017.
 */
public class RequestedServiceYear
{
    private String TotPagato;

    private String IDAZ;

    public String getIDS() {
        return IDS;
    }

    public void setIDS(String IDS) {
        this.IDS = IDS;
    }

    private String IDS;

    private String idServizio;

    private String NrTot;

    private String AnnoCompetenza;

    private String NrPagato;

    boolean lastElement;

    double finalTotal;

    String serviceName;

    String currentWebTitolo;

    String currentRequestedDate;

    String currentLastDateOfIntegration;

    String  currentDatePayment;

    String currentState;

    String currentPaymentTiming;

    String currentAmount;

    boolean currentYear;

    public String getNoteFiledValue() {
        return noteFiledValue;
    }

    public void setNoteFiledValue(String noteFiledValue) {
        this.noteFiledValue = noteFiledValue;
    }

    String noteFiledValue;

    public boolean getIsAttachementShow() {
        return isAttachementShow;
    }

    public void setIsAttachementShow(boolean isAttachementShow) {
        this.isAttachementShow = isAttachementShow;
    }

    boolean  isAttachementShow;

    public String getCurrentWebTitolo()
    {
        return currentWebTitolo;
    }

    public void setCurrentWebTitolo(String currentWebTitolo)
    {
        this.currentWebTitolo = currentWebTitolo;
    }

    public String getCurrentRequestedDate()
    {
        return currentRequestedDate;
    }

    public void setCurrentRequestedDate(String currentRequestedDate)
    {
        this.currentRequestedDate = currentRequestedDate;
    }

    public String getCurrentLastDateOfIntegration()
    {
        return currentLastDateOfIntegration;
    }

    public void setCurrentLastDateOfIntegration(String currentLastDateOfIntegration)
    {
        this.currentLastDateOfIntegration = currentLastDateOfIntegration;
    }

    public String getCurrentDatePayment()
    {
        return currentDatePayment;
    }

    public void setCurrentDatePayment(String currentDatePayment)
    {
        this.currentDatePayment = currentDatePayment;
    }

    public String getCurrentState()
    {
        return currentState;
    }

    public void setCurrentState(String currentState)
    {
        this.currentState = currentState;
    }

    public String getCurrentPaymentTiming()
    {
        return currentPaymentTiming;
    }

    public void setCurrentPaymentTiming(String currentPaymentTiming)
    {
        this.currentPaymentTiming = currentPaymentTiming;
    }

    public String getCurrentAmount()
    {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount)
    {
        this.currentAmount = currentAmount;
    }

    public boolean isCurrentYear()
    {
        return currentYear;
    }

    public void setCurrentYear(boolean currentYear)
    {
        this.currentYear = currentYear;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public boolean isLastElement()
    {
        return lastElement;
    }

    public void setLastElement(boolean lastElement)
    {
        this.lastElement = lastElement;
    }

    public double getFinalTotal()
    {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal)
    {
        this.finalTotal = finalTotal;
    }

    public String getTotPagato ()
    {
        return TotPagato;
    }

    public void setTotPagato (String TotPagato)
    {
        this.TotPagato = TotPagato;
    }

    public String getIDAZ ()
    {
        return IDAZ;
    }

    public void setIDAZ (String IDAZ)
    {
        this.IDAZ = IDAZ;
    }

    public String getIdServizio ()
    {
        return idServizio;
    }

    public void setIdServizio (String idServizio)
    {
        this.idServizio = idServizio;
    }

    public String getNrTot ()
    {
        return NrTot;
    }

    public void setNrTot (String NrTot)
    {
        this.NrTot = NrTot;
    }

    public String getAnnoCompetenza ()
    {
        return AnnoCompetenza;
    }

    public void setAnnoCompetenza (String AnnoCompetenza)
    {
        this.AnnoCompetenza = AnnoCompetenza;
    }

    public String getNrPagato ()
    {
        return NrPagato;
    }

    public void setNrPagato (String NrPagato)
    {
        this.NrPagato = NrPagato;
    }
}
