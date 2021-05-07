import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { TypographyComponent } from '../../typography/typography.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { UpgradeComponent } from '../../upgrade/upgrade.component';
import { RafflesComponent } from 'app/raffles/raffles.component';
import { UsersPanelComponent } from 'app/users-panel/users-panel.component';
import { RaffleEditComponent } from 'app/raffle-edit/raffle-edit.component';
import { AuthGuard } from 'app/auth.guard';

export const AdminLayoutRoutes: Routes = [
   
    { path: 'dashboard',      component: DashboardComponent },
    { path: 'user-profile',   component: UserProfileComponent },
    { path: 'table-list',     component: TableListComponent },
    { path: 'typography',     component: TypographyComponent },
    { path: 'icons',          component: IconsComponent },
    { path: 'maps',           component: MapsComponent },
    { path: 'notifications',  component: NotificationsComponent },
    { path: 'raffles',        component: RafflesComponent },
    { path: 'upgrade',        component: UpgradeComponent },
    { path: 'users-panel',    component: UsersPanelComponent, canActivate: [AuthGuard], data:{ roles: ['ROLE_ADMIN']} },
    { path: 'raffles/edit',   component: RaffleEditComponent, canActivate: [AuthGuard], data:{ roles: ['ROLE_ADMIN']} },
    { path: 'raffles/edit/:id',   component: RaffleEditComponent, canActivate: [AuthGuard], data:{ roles: ['ROLE_ADMIN']} }
];
