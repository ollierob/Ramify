import * as React from "react";
import {Icon} from "antd";
import {HasClass} from "../style/HasClass";

type IconProps = HasClass & {
    onClick?: React.MouseEventHandler<HTMLElement>
};

export const TreeIcon = (props: IconProps) => <Icon type="apartment" {...props}/>;
export const PeopleIcon = (props: IconProps) => <Icon type="user" {...props}/>;
export const PlacesIcon = (props: IconProps) => <Icon type="global" {...props}/>;
export const RecordsIcon = (props: IconProps) => <Icon type="read" {...props}/>;

export const FavouritesIcon = (props: IconProps) => <Icon type="star" {...props}/>;
