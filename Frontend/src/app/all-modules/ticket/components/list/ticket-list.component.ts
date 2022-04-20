import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    // run every 5 sec
    setInterval(() => {
      console.log('tick');
    }
    , 5000);

  }

}
