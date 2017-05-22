package io.oasp.application.mtsj.bookingmanagement.dataaccess.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import io.oasp.application.mtsj.bookingmanagement.common.api.Booking;
import io.oasp.application.mtsj.bookingmanagement.common.api.datatype.BookingType;
import io.oasp.application.mtsj.general.dataaccess.api.ApplicationPersistenceEntity;

@Entity
@Table(name = "Booking")
public class BookingEntity extends ApplicationPersistenceEntity implements Booking {

  private String name;

  private String bookingToken;

  private String comment;

  private String email;

  @NotNull
  @Future
  private Timestamp bookingDate;

  private Timestamp expirationDate;

  private Timestamp creationDate;

  private Boolean canceled;

  private BookingType bookingType;

  private TableEntity table;

  private List<InvitedGuestEntity> invites;

  private static final long serialVersionUID = 1L;

  public BookingEntity() {

    super();
    this.invites = new LinkedList<>();
    this.canceled = false;

  }

  /**
   * @return name
   */
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getName}.
   */
  @Override
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @return bookingToken
   */
  @Column(name = "bookingToken", unique = true)
  @Override
  public String getBookingToken() {

    return this.bookingToken;
  }

  /**
   * @param bookingToken new value of {@link #getBookingToken}.
   */
  @Override
  public void setBookingToken(String bookingToken) {

    this.bookingToken = bookingToken;
  }

  /**
   * @return comment
   */
  @Override
  public String getComment() {

    return this.comment;
  }

  /**
   * @param comment new value of {@link #getComment}.
   */
  @Override
  public void setComment(String comment) {

    this.comment = comment;
  }

  /**
   * @return bookingDate
   */
  @Override
  public Timestamp getBookingDate() {

    return this.bookingDate;
  }

  /**
   * @param bookingDate new value of {@link #getBookingDate}.
   */
  @Override
  public void setBookingDate(Timestamp bookingDate) {

    this.bookingDate = bookingDate;
  }

  /**
   * @return expirationDate
   */
  @Override
  public Timestamp getExpirationDate() {

    return this.expirationDate;
  }

  /**
   * @param expirationDate new value of {@link #getExpirationDate}.
   */
  @Override
  public void setExpirationDate(Timestamp expirationDate) {

    this.expirationDate = expirationDate;
  }

  /**
   * @return creationDate
   */
  @Override
  public Timestamp getCreationDate() {

    return this.creationDate;
  }

  /**
   * @param creationDate new value of {@link #getCreationDate}.
   */
  @Override
  public void setCreationDate(Timestamp creationDate) {

    this.creationDate = creationDate;
  }

  /**
   * @return canceled
   */
  @Override
  public boolean isCanceled() {

    return this.canceled;
  }

  /**
   * @param canceled new value of {@link #isCanceled}.
   */
  @Override
  public void setCanceled(boolean canceled) {

    this.canceled = canceled;
  }

  @Override
  @Transient
  public Long getTableId() {

    if (this.table == null) {
      return null;
    }
    return this.table.getId();
  }

  @Override
  public void setTableId(Long tableId) {

    if (tableId == null) {
      this.table = null;
    } else {
      TableEntity tableEntity = new TableEntity();
      tableEntity.setId(tableId);
      this.table = tableEntity;
    }
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "idTable")
  public TableEntity getTable() {

    return this.table;
  }

  public void setTable(TableEntity table) {

    this.table = table;
  }

  @Override
  public BookingType getBookingType() {

    return this.bookingType;
  }

  @Override
  public void setBookingType(BookingType bookingType) {

    this.bookingType = bookingType;
  }

  @Override
  public String getEmail() {

    return this.email;
  }

  @Override
  public void setEmail(String email) {

    this.email = email;

  }

  /**
   * @return invites
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @Column(name = "idBooking")
  @JoinTable(name = "InvitedGuest")
  public List<InvitedGuestEntity> getInvited() {

    return this.invites;
  }

  /**
   * @param invites new value of {@link #getInvites}.
   */
  public void setInvited(List<InvitedGuestEntity> invites) {

    this.invites = invites;
  }

  @Override
  public void setInvitedEmails(List<String> emails) {

    if (emails == null) {
      this.invites = null;
    } else {
      List<InvitedGuestEntity> list = new ArrayList<>(emails.size());
      for (String email : emails) {
        InvitedGuestEntity invited = new InvitedGuestEntity();
        invited.setEmail(email);
        list.add(invited);
      }
      this.invites = list;
    }
  }

  @Override
  @Transient
  public List<String> getInvitedEmails() {

    if (this.invites == null) {
      return new ArrayList<>();
    }
    List<String> result = new ArrayList<>(this.invites.size());
    for (InvitedGuestEntity invite : this.invites) {
      result.add(invite.getEmail());
    }
    return result;
  }

}
