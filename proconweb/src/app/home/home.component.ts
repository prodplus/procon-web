import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../core/user.service';
import { UsuarioDto } from '../models/dtos/usuario-dto';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  user$: Observable<UsuarioDto>;

  constructor(private userService: UserService, private router: Router) {
    this.user$ = this.userService.getUser();
  }

  ngOnInit(): void {}
}
