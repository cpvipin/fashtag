package com.org.fashtag.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.AppCoreConstants;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.common.UserPreference;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CommonDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerAddress;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.OrderHistory;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.OrdersStatus;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.ReturnProduct;
import com.org.fashtag.model.ReturnStatus;
import com.org.fashtag.model.Returns;
import com.org.fashtag.model.State;
import com.org.fashtag.service.AuthenticateService;
import com.org.fashtag.service.CustomerService;
import com.org.fashtag.service.HomeService;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;

public class CustomerServiceImpl extends BaseServiceImpl implements
		CustomerService {

	public CustomerDao customerDao;
	private ProductDao productDao;
	private CommonDao commonDao;
	private OrdersDao ordersDao;

	@Override
	public AppDTO addWishList(AppDTO appDTO) {

		Map dataMap = appDTO.getControllerMap();
		Customer customer = (Customer) dataMap.get("CUSTOMER");
		int productId = (Integer) dataMap.get("PRODUCT_ID");

		try {

			/* boolean isValidProduct=productDao.isProductExists(productId); */

			Product product = productDao.getProductById(productId);

			if (customer == null) {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("SHOULD_LOGIN_FOR_WISHLIST"));
			} else if (product == null) {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			}

			else {

				String wishList = customerDao.getWishListOfCustomer(customer);
				JSONObject wishObj;
				JSONArray jsonArray = new JSONArray();
				JSONObject prodObj = new JSONObject();

				if (!CommonUtils.isEmpty(wishList)) {

					wishObj = new JSONObject(wishList);
					jsonArray = wishObj.getJSONArray("wishList");
					/*
					 * Here if product exists nothing to do. Just alert back to
					 * user Product added
					 */
					if (!isProductExistInWishList(jsonArray, productId)) {
						prodObj.put("id", productId);
						jsonArray.put(prodObj);
						wishObj.put("wishList", jsonArray);
						/*
						 * customer from session doesnt have password Before
						 * update get customer
						 */
						customer = customerDao
								.getCustomerById(customer.getId());
						customer.setWishList(wishObj.toString());
						customerDao.update(customer);

						/*
						 * Update product likes
						 */

						int totalProdLikes = product.getTotalLikes();
						product.setTotalLikes(totalProdLikes + 1);
						productDao.update(product);

					}
				} else {
					wishObj = new JSONObject();
					prodObj.put("id", productId);
					jsonArray.put(prodObj);
					wishObj.put("wishList", jsonArray);

					/*
					 * customer from session doesnt have password Before update
					 * get customer
					 */
					customer = customerDao.getCustomerById(customer.getId());
					customer.setWishList(wishObj.toString());
					customerDao.update(customer);

					/*
					 * Update product likes
					 */

					int totalProdLikes = product.getTotalLikes();
					product.setTotalLikes(totalProdLikes + 1);
					productDao.update(product);

				}

				dataMap.put("WISH_LIST", wishList);
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				appDTO.setInfoMessage(getResourceMessage("WISHLIST_ADDED"));

			}
		} catch (Exception e) {
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			e.printStackTrace();
		}

		return appDTO;
	}

	@Override
	public AppDTO getUserPreference(AppDTO appDTO) {

		Map dataMap = appDTO.getControllerMap();
		Collection respColl = Collections.EMPTY_LIST;

		try {
			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);

			if (customer != null) {
				/*
				 * Load customer again-wish list added after log in will not be
				 * available in session
				 */
				customer = customerDao.getCustomerById(customer.getId());
				String resource = (String) dataMap.get("resource");

				UserPreference prefEnum = UserPreference.valueOf(resource);

				switch (prefEnum) {

				case WISHLIST:
					respColl = getUserWishList(customer);
					break;

				case PROFILE:
					respColl = getCustomerProfiles(customer);
					break;

				case INFO:
					dataMap.put("CUSTOMER", customer);
					break;

				case RETURNS:
					respColl = getCustomerReturns(customer);
					break;

				case ADDRESS:
					respColl = getCustomerAddress(customer);
					Collection stateColl = commonDao.findAll(State.class);
					dataMap.put("stateColl", stateColl);
					break;

				case RETURNORDER:
					int orderId = (Integer) dataMap.get("orderid");
					Orders orders = ordersDao.getOrderByOrderId(orderId);

					Collection orderReturnColl = ordersDao
							.findOptimizedListByCondition(Returns.class,
									new String[] { "id" },
									new String[] { "orders.id" },
									new Object[] { orders.getId() }, "id",
									true, true);

					if (orders.getCustomer().getId().intValue() != customer
							.getId().intValue()) {

						appDTO.setResponseStatus(ResponseStatus.ERROR);
						appDTO.setErrorMessage(getResourceMessage(NOT_AUTHORISED));

					} else if (orderReturnColl.size() > 0) {
						appDTO.setResponseStatus(ResponseStatus.ERROR);
						appDTO.setErrorMessage(getResourceMessage(ORDER_ALREADY_RETURNED));
					} else {

						respColl = orders.getOrderProduct();
						dataMap.put("orders", orders);
						appDTO.setInfoMessage(getResourceMessage(MAX_RET_DAYS_NOTIFY)
								+ " "
								+ getAppSystemProperties(AppCoreConstants.MAX_DAYS_FOR_RETURN));
						appDTO.setResponseStatus(ResponseStatus.SUCCESS);

					}

					break;

				default:
					respColl = getCustomerOrders(customer);
					break;

				}

			} else {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));
			}

		} catch (Exception e) {

			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			e.printStackTrace();
		}

		dataMap.put("respColl", respColl);
		appDTO.setControllerMap(dataMap);

		return appDTO;
	}

	public AppDTO getCustomerMeasurementAttributes(AppDTO appDTO) {
		try {
			Map dataMap = appDTO.getControllerMap();
			Customer customer = (Customer) dataMap.get("CUSTOMER");
			int gender = (Integer) dataMap.get("GENDER");
		/*	String profileName = (String) dataMap.get("PROFILENAME");*/
			
			Collection measurementColl = customerDao
					.getAllBodyMeasurementAttributesByGender(gender);

			JSONArray measurementArray = new JSONArray();
			Iterator iter = measurementColl.iterator();

			while (iter.hasNext()) {
				JSONObject jsonObj = new JSONObject();
				BodyMeasurementAttribute bodyMeasurementAttribute = (BodyMeasurementAttribute) iter
						.next();

				jsonObj.put("name", bodyMeasurementAttribute.getName());
				jsonObj.put("description",
						bodyMeasurementAttribute.getDescription());
				jsonObj.put("id", bodyMeasurementAttribute.getId());
				measurementArray.put(jsonObj);
			}

			dataMap.put("attrArray", measurementArray);
			appDTO.setControllerMap(dataMap);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appDTO;
	}

	/* methods */

	private AppDTO getOrderProductsForReturn(int orderId) {
		Orders orders = ordersDao.getOrderByOrderId(orderId);
		Customer customer = (Customer) getHttpSession().getAttribute(
				LOGGED_IN_CUSTOMER);
		AppDTO appDTO = new AppDTO();
		Map dataMap = new HashMap();

		if (orders.getCustomer().getId().intValue() == customer.getId()
				.intValue()) {

			Collection orderProductColl = orders.getOrderProduct();
			dataMap.put("orders", orders);
			dataMap.put("respColl", orderProductColl);
			appDTO.setInfoMessage(getResourceMessage(MAX_RET_DAYS_NOTIFY)
					+ " "
					+ getAppSystemProperties(AppCoreConstants.MAX_DAYS_FOR_RETURN));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);

		} else {
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage(NOT_AUTHORISED));
		}

		return appDTO;

	}

	private boolean isProductExistInWishList(JSONArray wishList, int prodId) {
		return wishList.toString().contains("\"id\":" + prodId);
	}

	private Collection getUserWishList(Customer customer) {
		Collection wishlistColl = new ArrayList();
		String wishStr = customer.getWishList();
		try {

			if (!CommonUtils.isEmpty(wishStr)) {
				JSONObject wishObj = new JSONObject(wishStr);
				JSONArray wishArr = wishObj.getJSONArray("wishList");

				for (int i = 0; i < wishArr.length(); i++) {

					JSONObject wishList = wishArr.getJSONObject(i);
					int prodId = wishList.getInt("id");
					Product product = productDao.getProductById(prodId);

					wishlistColl.add(product);

				}

			}

		} catch (JSONException je) {
			wishlistColl = Collections.EMPTY_LIST;
		}

		return wishlistColl;
	}

	private Collection getCustomerProfiles(Customer customer) {
		Collection custProfileColl = new ArrayList();
		custProfileColl = customerDao.getAllCustomerProfiles(customer);

		return custProfileColl;
	}

	private Collection getCustomerAddress(Customer customer) {

		Collection customerAddress = customerDao
				.getAllCustomerAddress(customer);
		return customerAddress;

	}

	private Collection getCustomerOrders(Customer customer) {
		Collection ordersColl = ordersDao.getOrdersByCustomer(customer);
		return ordersColl;
	}

	private Collection getCustomerReturns(Customer customer) {
		Collection retColl = customerDao.getReturnsByCustomer(customer);
		return retColl;
	}

	/* getters and setters */

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public AppDTO addNewCustomerProfile(AppDTO appDTO) {

		Map dataMap = appDTO.getControllerMap();

		try {
			String[] sizeAttrVal = (String[]) dataMap.get("sizeAttrVal");
			String[] sizeAttrId = (String[]) dataMap.get("sizeAttrId");
			String profileName = (String) dataMap.get("profileName");
			int profileGender = (Integer) dataMap.get("profileGender");

			TransactionManager.begin();

			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);
			CustomerProfile customerProfile = new CustomerProfile();
			customerProfile.setCustomer(customer);
			customerProfile.setDateAdded(DateUtils.getCurrentDate());
			customerProfile.setDateModified(DateUtils.getCurrentDate());
			customerProfile.setName(profileName);
			customerProfile.setIsDefault(false);
			customerProfile.setGender(profileGender);

			customerDao.createCustomeProfile(customerProfile);

			for (int i = 0; i < sizeAttrId.length; i++) {
				int attrId = Integer.parseInt(sizeAttrId[i]);
				int attrVal = 0;

				if (!CommonUtils.isEmpty(sizeAttrVal[i])) {
					attrVal = Integer.parseInt(sizeAttrVal[i]);
				}

				CustomerBodyMeasurement customerMeasurement = new CustomerBodyMeasurement();
				BodyMeasurementAttribute bodyMeasurement = customerDao
						.getBodyBodyMeasurementAttributeBbyId(attrId);
				customerMeasurement
						.setBodyMeasurementAttribute(bodyMeasurement);
				customerMeasurement.setCustomerProfile(customerProfile);
				customerMeasurement.setValue(attrVal);
				customerMeasurement.setUnit("CM");

				dataMap.put("CUSTOMER_BODY_MEASUREMENT", customerMeasurement);
				customerDao.createCustomerBodyMeasurement(appDTO);
			}

			appDTO.setInfoMessage(getResourceMessage("PROFILE_ADDED"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			TransactionManager.commit();
			TransactionManager.closeSession();

		}

		catch (Exception e) {
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));

			TransactionManager.rollback();
			TransactionManager.closeSession();
		}

		return appDTO;

	}

	public AppDTO editCustomerProfile(AppDTO appDTO) {
		try {

			Map dataMap = appDTO.getControllerMap();
			int profileId = (Integer) dataMap.get("profileId");
			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);

			CustomerProfile customerProfile = customerDao
					.getCustomerProfileByProfileId(profileId);

			/*
			 * check if customer authorized since values are coming via ajax
			 */
			if (customerProfile.getCustomer().getId().intValue() != customer
					.getId().intValue()) {
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));

			} else {
				dataMap.put("profileName", customerProfile.getName());
				dataMap.put("profileGender", customerProfile.getGender());

				Collection profileMeasurements = customerDao
						.getCustomerMeasurementByProfile(customerProfile);
				JSONArray measArr = new JSONArray();

				Iterator iter = profileMeasurements.iterator();
				while (iter.hasNext()) {
					JSONObject jsonObj = new JSONObject();
					Object[] measureAttr = (Object[]) iter.next();
					BodyMeasurementAttribute bodyMeasurementAttribute = (BodyMeasurementAttribute) measureAttr[1];
					CustomerBodyMeasurement customerBodyMeasurement = (CustomerBodyMeasurement) measureAttr[0];

					String customerMeasurement = "";
					if (customerBodyMeasurement != null) {
						customerMeasurement = ""
								+ customerBodyMeasurement.getValue();
					}

					jsonObj.put("name", bodyMeasurementAttribute.getName());
					jsonObj.put("description",
							bodyMeasurementAttribute.getDescription());
					jsonObj.put("id", bodyMeasurementAttribute.getId());
					jsonObj.put("customerValue", customerMeasurement);
					measArr.put(jsonObj);
				}

				dataMap.put("measArr", measArr);
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				// appDTO.setInfoMessage(getResourceMessage(""));
			}

		} catch (Exception e) {
			e.printStackTrace();
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
		}

		return appDTO;
	}

	public AppDTO updateCustomerProfile(AppDTO appDTO) {

		Map dataMap = appDTO.getControllerMap();
		int profileId = (Integer) dataMap.get("profileId");
		int profileGender = (Integer) dataMap.get("profileGender");

		String profileName = (String) dataMap.get("profileName");
		String[] sizeAttrVal = (String[]) dataMap.get("SIZEATTRVAL");
		String[] sizeAttrId = (String[]) dataMap.get("SIZEATTRID");

		Customer customer = (Customer) getHttpSession().getAttribute(
				LOGGED_IN_CUSTOMER);
		CustomerProfile custProf = customerDao
				.getCustomerProfileByProfileId(profileId);

		if (customer.getId().intValue() == custProf.getCustomer().getId()
				.intValue()) {
			TransactionManager.begin();

			custProf.setGender(profileGender);
			custProf.setName(profileName);
			customerDao.updateCustomerProfile(custProf);
			customerDao.deleteCustomerMeasurementsByProfile(profileId);

			for (int i = 0; i < sizeAttrId.length; i++) {
				int attrId = Integer.parseInt(sizeAttrId[i]);
				int attrVal = 0;

				if (!CommonUtils.isEmpty(sizeAttrVal[i])) {
					attrVal = Integer.parseInt(sizeAttrVal[i]);
				}
				CustomerBodyMeasurement customerMeasurement = new CustomerBodyMeasurement();
				BodyMeasurementAttribute bodyMeasurement = customerDao
						.getBodyBodyMeasurementAttributeBbyId(attrId);
				customerMeasurement
						.setBodyMeasurementAttribute(bodyMeasurement);
				customerMeasurement.setCustomerProfile(custProf);
				customerMeasurement.setValue(attrVal);
				customerMeasurement.setUnit("CM");

				dataMap.put("CUSTOMER_BODY_MEASUREMENT", customerMeasurement);
				customerDao.createCustomerBodyMeasurement(appDTO);
			}
			TransactionManager.commit();
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			appDTO.setInfoMessage(getResourceMessage("MEASUREMENT_ADDED"));

		}

		return appDTO;

	}

	public AppDTO deleteCustomerProfile(AppDTO appDTO) {
		try {
			Map dataMap = appDTO.getControllerMap();
			int profileId = (Integer) dataMap.get("profileId");

			CustomerProfile custProfile = customerDao
					.getCustomerProfileByProfileId(profileId);

			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);
			if (customer.getId().intValue() == custProfile.getCustomer()
					.getId().intValue()) {

				if (custProfile.getIsDefault()) {
					appDTO.setResponseStatus(ResponseStatus.ERROR);
					appDTO.setErrorMessage(getResourceMessage("DEFAULT_PROFILE_CANNOT_DELETE"));
				}

				else {
					TransactionManager.begin();
					customerDao.deleteCustomerMeasurementsByProfile(profileId);
					customerDao.deleteCustomerProfile(custProfile);

					TransactionManager.commit();
					TransactionManager.closeSession();
					appDTO.setInfoMessage(getResourceMessage("PROFILE_DELETED"));
					appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				}
			} else {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));
			}
		} catch (Exception e) {
			TransactionManager.rollback();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));

		}

		return appDTO;

	}

	public AppDTO chooseDefaultProfile(AppDTO appDTO) {
		try {

			Map dataMap = appDTO.getControllerMap();
			int profileId = (Integer) dataMap.get("profileId");

			CustomerProfile custProfile = customerDao
					.getCustomerProfileByProfileId(profileId);

			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);
			if (customer.getId().intValue() == custProfile.getCustomer()
					.getId().intValue()) {
				TransactionManager.begin();
				CustomerProfile customerProfile = customerDao
						.getDefaultProfile(customer);

				if (customerProfile != null) {
					/* set default of old one to false */
					customerProfile.setIsDefault(false);
					customerDao.updateCustomerProfile(customerProfile);
				}

				/* set default of new one to true */
				custProfile.setIsDefault(true);
				customerDao.updateCustomerProfile(custProfile);

				TransactionManager.commit();
				dataMap.put("profileId", custProfile.getId());
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				appDTO.setInfoMessage(getResourceMessage("DEFAULT_PROFILE_SELECTED"));

			} else {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));
			}

		} catch (Exception e) {
			TransactionManager.rollback();

			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));
			e.printStackTrace();
		}
		return appDTO;
	}

	public AppDTO addCustomerAddress(AppDTO appDTO) {

		try {

			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);
			if (customer == null) {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORIZED"));
			} else {
				Map dataMap = appDTO.getControllerMap();
				CustomerAddress customerAddress = new CustomerAddress();
				customerAddress.setFullName((String) dataMap.get("fullName"));
				customerAddress.setAddress((String) dataMap.get("address"));
				customerAddress.setLocality((String) dataMap.get("locality"));
				customerAddress.setMobile((String) dataMap.get("mobile"));
				customerAddress.setPinCode((String) dataMap.get("pinCode"));
				State state = commonDao.getStateById((Integer) dataMap
						.get("state"));
				customerAddress.setState(state);
				customerAddress.setCustomer(customer);

				customerDao.add(customerAddress);
				dataMap.clear();
				appDTO.setControllerMap(dataMap);
				appDTO.setInfoMessage(getResourceMessage("ADDRESS_ADDED"));
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}

		} catch (Exception e) {
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_EXCEPTION"));
			e.printStackTrace();
		}
		return appDTO;
	}

	@Override
	public AppDTO deleteCustomerAddress(AppDTO appDTO) {
		try {
			Map dataMap = appDTO.getControllerMap();
			int addressId = (Integer) dataMap.get("addressId");

			CustomerAddress custAddr = customerDao
					.getCustomerAddressById(addressId);

			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);
			if (customer.getId().intValue() == custAddr.getCustomer().getId()
					.intValue()) {
				customerDao.delete(custAddr);
				appDTO.setInfoMessage(getResourceMessage("ADDRESS_DELETED"));
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);

			} else {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));
			}
		} catch (Exception e) {
			TransactionManager.rollback();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));

		}

		return appDTO;
	}

	public AppDTO updateCustomerAddress(AppDTO appDTO) {

		try {

			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);
			Map dataMap = appDTO.getControllerMap();
			CustomerAddress customerAddress = customerDao
					.getCustomerAddressById((Integer) dataMap.get("addressId"));
			if (customer == null || customerAddress == null) {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORIZED"));
			} else {

				customerAddress.setFullName((String) dataMap.get("fullName"));
				customerAddress.setAddress((String) dataMap.get("address"));
				customerAddress.setLocality((String) dataMap.get("locality"));
				customerAddress.setMobile((String) dataMap.get("mobile"));
				customerAddress.setPinCode((String) dataMap.get("pinCode"));
				State state = commonDao.getStateById((Integer) dataMap
						.get("state"));
				customerAddress.setState(state);
				customerAddress.setCustomer(customer);

				customerDao.update(customerAddress);
				dataMap.clear();
				appDTO.setControllerMap(dataMap);
				appDTO.setInfoMessage(getResourceMessage("ADDRESS_ADDED"));
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}

		} catch (Exception e) {
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_EXCEPTION"));
			e.printStackTrace();
		}
		return appDTO;
	}

	@Override
	public AppDTO updateUserInfo(AppDTO appDTO) {
		try {
			Map dataMap = appDTO.getControllerMap();
			Customer cust = (Customer) dataMap.get("CUSTOMER");
			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);
			customer = customerDao.getCustomerById(customer.getId());

			customer.setEmail(cust.getEmail());
			customer.setPhone(cust.getPhone());
			customer.setFullName(cust.getFullName());
			customerDao.update(customer);

			appDTO.setInfoMessage(getResourceMessage("USER_INFO_UPDATED"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			e.printStackTrace();

		}

		return appDTO;
	}

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public OrdersDao getOrdersDao() {
		return ordersDao;
	}

	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}

	@Override
	public AppDTO returnOrderProducts(AppDTO appDTO) {

		Map dataMap = appDTO.getControllerMap();
		try {
			int orderid = (Integer) dataMap.get("orderid");
			String[] retProdArr = (String[]) dataMap.get("retProdArr");
			Orders order = ordersDao.getOrderByOrderId(orderid);
			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);

			Date orderDate = order.getDateAdded();
			Date today = DateUtils.getCurrentDate();
			int maxRetDays = Integer
					.parseInt(getAppSystemProperties(AppCoreConstants.MAX_DAYS_FOR_RETURN));
			boolean isRetProdExists = false;

			StringBuffer nonRetProdNames = new StringBuffer("");

			if (customer.getId().intValue() != order.getCustomer().getId()
					.intValue()) {

				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(NOT_AUTHORISED));
				return appDTO;

			} else if (DateUtils.differenceInDays(orderDate, today) > maxRetDays) {
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("This order cannot be returned any more , "
						+ getResourceMessage(MAX_RET_DAYS_NOTIFY)));

				return appDTO;
			}

			BigDecimal totalRetAmount = new BigDecimal("0");
			Returns returns = new Returns();
			returns.setCustomer(customer);
			returns.setOrders(order);
			returns.setDateAdded(DateUtils.getCurrentDate());
			returns.setDateModified(DateUtils.getCurrentDate());

			ReturnStatus returnStatus = productDao
					.getReturnStatusByName(AppCoreConstants.RETURN_STATUS_PENDING);
			returns.setReturnStatus(returnStatus);

			productDao.addOrderReturn(returns);

			int retId = returns.getId();

			TransactionManager.begin();

			for (String id : retProdArr) {

				int orderProdId = Integer.parseInt(id);
				OrderProduct orderProd = ordersDao
						.getOrderProductById(orderProdId);
				if (orderProd.getSize().getName().equalsIgnoreCase("custom")) {
					if (CommonUtils.isEmpty(nonRetProdNames.toString())) {
						nonRetProdNames.append(" and ");
					}
					nonRetProdNames.append(orderProd.getProduct().getName());

				} else {
					ReturnProduct returnProd = new ReturnProduct();
					returnProd.setProduct(orderProd.getProduct());
					returnProd.setReturns(returns);
					returnProd.setQuantity(orderProd.getQuantity());
					returnProd.setTotalAmount(orderProd.getTotalAmount());
					returnProd.setUnitPrice(orderProd.getUnitPrice());

					totalRetAmount.add(orderProd.getTotalAmount());
					TransactionManager.txCreate(returnProd);

					isRetProdExists = true;

				}

			}

			/* update returns for total amount returned */

			returns.setTotalAmount(totalRetAmount);
			TransactionManager.txUpdate(returns);

			if (!isRetProdExists) {
				appDTO.setInfoMessage(getResourceMessage(nonRetProdNames
						.toString()
						+ " cannot be returned - Custom size orders cannot be returned."));
				TransactionManager.txDelete(returns);
				TransactionManager.rollback();
			}
			if (CommonUtils.isEmpty(nonRetProdNames.toString())) {
				appDTO.setInfoMessage(getResourceMessage(PRODUCT_RETURNED));
			}

			TransactionManager.commit();

		} catch (Exception e) {

			TransactionManager.rollback();
			e.printStackTrace();
		}
		return appDTO;
	}

	public AppDTO cancelOrder(AppDTO appDTO) {

		Map dataMap = appDTO.getControllerMap();
		try {
			int orderid = (Integer) dataMap.get("orderid");
			Orders orders = ordersDao.getOrderByOrderId(orderid);
			Customer customer = (Customer) getHttpSession().getAttribute(
					LOGGED_IN_CUSTOMER);

			if (!(orders.getCustomer().getId().intValue() == customer.getId()
					.intValue())) {
				appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));
				appDTO.setResponseStatus(ResponseStatus.ERROR);
			}
			if (!orders.getOrdersStatus().getName()
					.equals(AppCoreConstants.ORDER_STATUS_PENDING)) {

				appDTO.setErrorMessage(getResourceMessage(CANNOT_CANCEL_ORDER));
				appDTO.setResponseStatus(ResponseStatus.ERROR);

			} else {
				OrdersStatus orderStat = ordersDao
						.getOrderStatusByName(AppCoreConstants.ORDER_STATUS_CANCELLED);

				TransactionManager.begin();

				OrderHistory orderHist = new OrderHistory();
				orderHist.setOrder(orders);
				orderHist.setOrdersStatus(orderStat);
				orderHist.setNotify(false);
				orderHist.setDateAdded(DateUtils.getCurrentDate());
				orderHist.setComment("Cancelled by " + customer.getFullName());
				orderHist.setOrdersStatus(orderStat);
				TransactionManager.txCreate(orderHist);

				orders.setOrdersStatus(orderStat);
				TransactionManager.txUpdate(orders);

				TransactionManager.commit();

				appDTO.setInfoMessage(getResourceMessage(ORDER_CANCEL));
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);

			}
		} catch (Exception e) {
			TransactionManager.rollback();
			appDTO.setErrorMessage("UNEXPECTED_ERROR");
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}

		return appDTO;

	}

}
