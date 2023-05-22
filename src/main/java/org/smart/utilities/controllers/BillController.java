package org.smart.utilities.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.smart.utilities.dto.BillDTO;
import org.smart.utilities.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BillController {

  private final BillService billService;

  @Autowired
  public BillController(BillService billService) {
    this.billService = billService;
  }

  @PostMapping("admin/bill")
  @ResponseStatus(HttpStatus.CREATED)
  public BillDTO createAdminBill(@RequestBody BillDTO bill) {
    return billService.createAdminBill(bill);
  }

  @GetMapping("admin/bills")
  @ResponseStatus(HttpStatus.OK)
  public List<BillDTO> getAllBills(@RequestParam("paid") Boolean paid) {
    return billService.getAllBills(paid);
  }

  @PostMapping("bill")
  @ResponseStatus(HttpStatus.CREATED)
  public BillDTO createBill(@RequestBody BillDTO bill, HttpServletRequest request)
      throws Exception {
    return billService.createBill(bill, request);
  }

  @PostMapping("bill/pay/{billId}")
  @ResponseStatus(HttpStatus.OK)
  public void payBill(@PathVariable Integer billId) throws Exception {
    billService.payBill(billId);
  }

  @GetMapping("bills")
  @ResponseStatus(HttpStatus.OK)
  public List<BillDTO> getBillsPerType(@RequestParam("billType") String billType,
      @RequestParam("paid") Boolean paid, HttpServletRequest request)
      throws Exception {
    return billService.getBillsPerType(billType, paid, request);
  }

  @GetMapping("bills/all")
  @ResponseStatus(HttpStatus.OK)
  public List<BillDTO> getAllBills(@RequestParam("paid") Boolean paid, HttpServletRequest request)
      throws Exception {
    return billService.findAllBills(request, paid);
  }
}
