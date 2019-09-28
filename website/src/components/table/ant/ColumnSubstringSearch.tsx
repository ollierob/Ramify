import * as React from "react";
import {ColumnProps, FilterDropdownProps} from "antd/es/table";
import {Button, Icon, Input} from "antd";
import "./Table.css";

export function ColumnSubstringSearch<T>(placeholder: string, toString: (t: T) => string): Partial<ColumnProps<T>> {
    return {
        filterIcon: filtered => <Icon type="search" className={filtered ? "active" : ""}/>,
        filterDropdown: (props: FilterDropdownProps) => <div className="filter">
            <Input
                autoFocus
                value={props.selectedKeys[0]}
                placeholder={placeholder}
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
                onClick={() => props.clearFilters(props.selectedKeys)}>
                Clear
            </Button>
        </div>,
        onFilter: (value, record) => {
            const r = toString(record);
            if (!r) return false;
            return r.toLowerCase().includes(value.toLowerCase());
        },
        onFilterDropdownVisibleChange: visible => {
            //TODO focus on input
        }
    };
}