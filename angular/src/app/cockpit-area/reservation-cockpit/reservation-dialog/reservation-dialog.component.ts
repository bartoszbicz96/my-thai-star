import { FriendsInvite } from '../../../shared/backend/booking/bookingInfo';
import { ReservationView } from '../../../shared/models/interfaces';
import { Component, OnInit, Inject } from '@angular/core';
import { IPageChangeEvent, ITdDataTableColumn, TdDataTableService } from '@covalent/core';
import { ReservationCockpitService } from '../shared/reservation-cockpit.service';
import {MD_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'cockpit-reservation-dialog',
  templateUrl: './reservation-dialog.component.html',
  styleUrls: ['./reservation-dialog.component.scss'],
})
export class ReservationDialogComponent implements OnInit {

  data: any;

  datat: ReservationView[] = [];

  columnst: ITdDataTableColumn[] = [
    { name: 'date', label: 'Reservation date'},
    { name: 'hour', label: 'Reservation hour'},
    { name: 'creationDate', label: 'Creation date'},
    { name: 'creationHour', label: 'Creation time'},
    { name: 'nameOwner', label: 'Owner' },
    { name: 'emailOwner', label: 'Email' },
    { name: 'bookingId', label: 'Table'},
  ];

  datao: FriendsInvite[] = [];

  columnso: ITdDataTableColumn[] = [
    { name: 'email', label: 'Guest email'},
    { name: 'acceptance', label: 'Acceptances and declines'},
  ];

  fromRow: number = 1;
  currentPage: number = 1;
  pageSize: number = 5;
  filteredData: any[] = this.datao;

  constructor(private _dataTableService: TdDataTableService,
              private reservationCockpitService: ReservationCockpitService,
              @Inject(MD_DIALOG_DATA) dialogData: any) {
                 this.data = dialogData.row;
  }

  ngOnInit(): void {
    this.reservationCockpitService.getReservation(this.data.bookingId).subscribe( (reservation: ReservationView) => {
      this.datat.push(reservation);
      this.datao = reservation.friends;
    });
    this.filter();
  }

  page(pagingEvent: IPageChangeEvent): void {
    this.fromRow = pagingEvent.fromRow;
    this.currentPage = pagingEvent.page;
    this.pageSize = pagingEvent.pageSize;
    this.filter();
  }

  filter(): void {
    let newData: any[] = this.datao;
    newData = this._dataTableService.pageData(newData, this.fromRow, this.currentPage * this.pageSize);
    this.filteredData = newData;
  }

}