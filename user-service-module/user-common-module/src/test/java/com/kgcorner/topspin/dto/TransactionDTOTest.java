package com.kgcorner.topspin.dto;

import com.kgcorner.topspin.model.AbstractTransaction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/08/21
 */

public class TransactionDTOTest {
    private TransactionDTO transactionDTO;
    private String id = "id";
    private Date date = new Date();
    private AbstractTransaction.TRANSACTION_KIND transactionKind = AbstractTransaction.TRANSACTION_KIND.CREDIT;
    private AbstractTransaction.TRANSACTION_ACTION transactionAction = AbstractTransaction.TRANSACTION_ACTION.CASHBACK;
    private String userId = "userId";
    private AbstractTransaction.TRANSACTION_STATE transactionState = AbstractTransaction.TRANSACTION_STATE.CREDIT_PENDING;
    private double totalAmount = 90.0;
    private List<String> moderatorRemark = new ArrayList<>();
    private List<AbstractTransaction.Item> items = new ArrayList<>();
    private double awardedAmount = 10;
    private double redeemedAmount = 10;
    @Before
    public void setUp() throws Exception {
        transactionDTO = new TransactionDTO();
    }

    @Test
    public void getLinks() {
        transactionDTO.addLink("href","self");
        assertEquals("href", transactionDTO.getLinks().get(0).getHref());
        assertEquals("self", transactionDTO.getLinks().get(0).getRel());
    }

    @Test
    public void testTransaction() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(transactionKind);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        assertEquals(awardedAmount, transactionDTO.getAwardedAmount(), 0.1);
        assertEquals(date, transactionDTO.getDate());
        assertEquals(id, transactionDTO.getId());
        assertEquals(items, transactionDTO.getItems());
        assertEquals(moderatorRemark, transactionDTO.getModeratorRemark());
        assertEquals(redeemedAmount, transactionDTO.getRedeemedAmount(), 0.1);
        assertEquals(transactionAction, transactionDTO.getTransactionAction());
        assertEquals(totalAmount, transactionDTO.getTotalAmount(), 0.1);
        assertEquals(transactionState, transactionDTO.getTransactionState());
        assertEquals(transactionKind, transactionDTO.getTransactionKind());
        assertEquals(userId, transactionDTO.getUserId());
    }

    @Test
    public void testValidate() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(0);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(transactionKind);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
        //This assert is just in order to avoid minimum one assert statement requirement of sonar
        assertEquals(userId, transactionDTO.getUserId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateDate() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(transactionKind);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTotalAmount() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(0);
        transactionDTO.setTransactionKind(transactionKind);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTransactionKindForNull() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTransactionStateForNull() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(transactionKind);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTransactionActionForNull() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(transactionKind);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateAwardedAmount() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateRedeemedAmount() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(0);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(transactionAction);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTransactionKindAgainstAction() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(0);
        transactionDTO.setTransactionAction(AbstractTransaction.TRANSACTION_ACTION.REDEEM);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
        transactionDTO.setTransactionState(transactionState);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTransactionKindAgainstState() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(awardedAmount);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(0);
        transactionDTO.setTransactionAction(AbstractTransaction.TRANSACTION_ACTION.REDEEM);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.CREDIT);
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.REDEEM_CANCELLED);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTransactionKindAgainstCreditState() {
        String remark = "moderatorRemark";
        moderatorRemark.add(remark);
        AbstractTransaction.Item item = new AbstractTransaction.Item();
        item.setLink("link");
        item.setName("Product");
        item.setPrice(10.0);
        items.add(item);
        transactionDTO.setAwardedAmount(0);
        transactionDTO.setDate(date);
        transactionDTO.setId(id);
        transactionDTO.setItems(items);
        transactionDTO.setModeratorRemark(moderatorRemark);
        transactionDTO.setRedeemedAmount(redeemedAmount);
        transactionDTO.setTransactionAction(AbstractTransaction.TRANSACTION_ACTION.REDEEM);
        transactionDTO.setTotalAmount(totalAmount);
        transactionDTO.setTransactionKind(AbstractTransaction.TRANSACTION_KIND.DEBIT);
        transactionDTO.setTransactionState(AbstractTransaction.TRANSACTION_STATE.CREDIT_COMPLETED);
        transactionDTO.setUserId(userId);
        transactionDTO.validate();
    }


}