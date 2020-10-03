import * as React from "react";
import {HasClass} from "../style/HasClass";
import {Icon} from "antd";

type IconProps = HasClass & {
    title?: string,
    onClick?: React.MouseEventHandler<HTMLElement>
};

export const TreeIcon = (props: IconProps) => <Icon type="apartment" {...props}/>;
export const AddTreeIcon = (props: IconProps) => <Icon type="file-add" {...props}/>;

export const PersonIcon = (props: IconProps) => <Icon type="user" {...props}/>;
export const PeopleIcon = (props: IconProps) => <TreeIcon {...props}/>;

export const PlacesIcon = (props: IconProps) => <Icon type="global" {...props}/>;
export const RecordsIcon = (props: IconProps) => <Icon type="read" {...props}/>;
export const ArchiveIcon = (props: IconProps) => <Icon type="export" {...props}/>;

export const FavouritesIcon = (props: IconProps) => <Icon type="star" {...props}/>;
export const SearchIcon = (props: IconProps) => <Icon type="search" {...props}/>;
export const CancelIcon = (props: IconProps) => <Icon type="close" {...props}/>;
export const LoadingIcon = (props: IconProps) => <Icon type="loading" {...props}/>;
export const ViewIcon = (props: IconProps) => <Icon type="eye" {...props}/>;
export const FilterIcon = (props: IconProps) => <Icon type="filter" {...props}/>;
export const ImageIcon = (props: IconProps) => <Icon type="file-image" {...props}/>;
export const StatisticsIcon = (props: IconProps) => <Icon type="experiment" {...props}/>;

export const DeleteIcon = (props: IconProps) => <Icon type="delete" {...props}/>;
export const MergeIcon = (props: IconProps) => <Icon type="swap" {...props}/>;

export const PrevIcon = (props: IconProps) => <Icon type="left-square" {...props}/>;
export const NextIcon = (props: IconProps) => <Icon type="right-square" {...props}/>;
export const EditIcon = (props: IconProps) => <Icon type="edit" {...props}/>;

export const LinkIcon = (props: IconProps) => <Icon type="link" {...props}/>;