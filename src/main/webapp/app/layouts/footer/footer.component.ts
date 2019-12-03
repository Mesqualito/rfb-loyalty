import { Component } from '@angular/core';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html'
})
export class FooterComponent {
  cuttingYears: number;

  constructor() {
    this.cuttingYears = new Date().getFullYear() - 1921;
  }
}
