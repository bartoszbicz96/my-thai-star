import { Component, Input, OnInit } from '@angular/core';
import { SidenavSharedServiceService } from '../../sidenav/shared/sidenav-shared-service.service';
import * as _ from 'lodash';

@Component({
  selector: 'app-menu-card',
  templateUrl: './menu-card.component.html',
  styleUrls: ['./menu-card.component.scss']
})
export class MenuCardComponent implements OnInit {

  @Input('menu') menuInfo: any;

  constructor(
    private sidenav: SidenavSharedServiceService
  ) { }

  ngOnInit() { }

  openSidenav(): void {
    this.sidenav.openSideNav();
  }

  addOrderMenu(): void {
    this.sidenav.addOrder(this.menuInfo);
    this.openSidenav();
  }

  changeFavouriteState(): void {
    this.menuInfo.favourite = !this.menuInfo.favourite;
  }

  selectedOption(option): void {
    option.selected ? option.selected = false : option.selected = true;
  }

}
