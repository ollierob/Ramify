import * as React from "react";
import {ColumnProps, FilterDropdownProps} from "antd/es/table";
import {Button, Icon, Input} from "antd";
import "./Table.css";

export function ColumnSubstringLocalSearch<T>(toString: (t: T) => string): Partial<ColumnProps<T>> {
    return {
        ...ColumnSearch,
        onFilter: (value, record) => {
            const r = toString(record);
            if (!r) return false;
            return r.toLowerCase().includes(value.toLowerCase());
        },
    };
}

export const ColumnSearch: Readonly<Partial<ColumnProps<any>>> = {
    filterIcon: filtered => <Icon type="search" className={filtered ? "active" : ""}/>,
    filterDropdown: (props: FilterDropdownProps) => <div className="filter">
        <Input
            autoFocus
            value={props.selectedKeys[0]}
            placeholder="Search"
            onChange={e => props.setSelectedKeys(e.target.value ? [e.target.value] : [])}
            onPressEnter={props.confirm}/>
        <Button
            className="search"
            type="primary"
            icon="search"
            onClick={props.confirm}>
            Search
        </Button>
        <Button
            className="clear"
            onClick={() => props.clearFilters()}>
            Clear
        </Button>
    </div>,
    onFilterDropdownVisibleChange: visible => {
        //TODO focus on input
    }
};