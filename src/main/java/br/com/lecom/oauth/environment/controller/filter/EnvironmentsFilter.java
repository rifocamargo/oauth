package br.com.lecom.oauth.environment.controller.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.lecom.oauth.environment.validators.Hostname;
import io.swagger.annotations.ApiParam;

public class EnvironmentsFilter {
	
	@ApiParam(value = "Parâmetro que vai receber uma data inicial. No formato yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;

	@ApiParam(value = "Parâmetro que vai receber uma data final. No formato yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;

	@ApiParam(value = "Parâmetro que vai receber um domínio válido")
	@Hostname
	private String hostname;

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
