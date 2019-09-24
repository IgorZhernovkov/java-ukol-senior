package com.etnetera.hr.data.entity;

import com.etnetera.hr.data.dto.WithId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JavaScriptFramework implements WithId<Long> {
	/** ID of entity */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Name of framework */
	@Column(nullable = false, length = 30)
	private String name;

	/** Version's list */
	@OneToMany(cascade = {CascadeType.ALL})
	private List<FrameworkVersion> versions;

	/** hype level of framework */
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private HypeLevel hypeLevel;

	/** Deprecated date of framework */
	private LocalDate deprecatedDate;

	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
	}
		
}
