import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserHomeComponent } from './user-home/user-home.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { UsersListComponent } from './users-list/users-list.component';



@NgModule({
  declarations: [
    UserHomeComponent,
    CreateUserComponent,
    UpdateUserComponent,
    UsersListComponent
  ],
  imports: [
    CommonModule
  ]
})
export class UsersModule { }
