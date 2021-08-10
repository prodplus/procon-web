import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/auth/auth.service';
import { ModalComponent } from '../shared/modal/modal.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('inputLogin')
  inputLogin: ElementRef<HTMLInputElement>;
  isLoading = false;
  @ViewChild('modal')
  modal: ModalComponent;
  loginForm = this.builder.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  constructor(
    private builder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.inputLogin.nativeElement.focus();
    }, 100);
  }

  login() {
    this.isLoading = true;
    const email = this.loginForm.get('email').value;
    const password = this.loginForm.get('password').value;

    this.authService.authenticate(email, password).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
        this.loginForm.reset();
        this.inputLogin.nativeElement.focus();
      },
      complete: () => {
        this.isLoading = false;
        this.router.navigate(['home']);
      },
    });
  }
}
