package info.dao;

import java.util.List;

import java.util.Map;

import javax.ejb.Local;

import info.entities.Customer;

@Local
public interface ICustomerDAO {

	public void createCustomer(Customer customer);

	public Customer find(Integer id);

	public void remove(Customer customer);

	public Customer merge(Customer customer);

	public List<Customer> getSearchList(Map<String, Object> searchParams);

}
