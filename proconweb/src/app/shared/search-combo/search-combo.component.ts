import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-search-combo',
  templateUrl: './search-combo.component.html',
  styleUrls: ['./search-combo.component.css'],
})
export class SearchComboComponent implements OnInit {
  @Input() form: FormGroup;
  @Input() label: string;
  @Input() options: [{ l: string; value: string }];
  icon = faSearch;

  constructor() {}

  ngOnInit(): void {}
}
