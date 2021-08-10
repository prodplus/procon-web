import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from '../core/user.service';
import { UsuarioDto } from '../models/dtos/usuario-dto';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  user$: Observable<UsuarioDto>;

  constructor(private userService: UserService) {
    this.user$ = this.userService.getUser();
  }

  ngOnInit(): void {}

  logout() {
    this.userService.logout();
  }
}
