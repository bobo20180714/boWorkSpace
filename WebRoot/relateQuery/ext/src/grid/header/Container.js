/**
 * Container which holds headers and is docked at the top or bottom of a TablePanel.
 * The HeaderContainer drives resizing/moving/hiding of columns within the TableView.
 * As headers are hidden, moved or resized the headercontainer is responsible for
 * triggering changes within the view.
 */
Ext.define('Ext.grid.header.Container', {
    extend: 'Ext.container.Container',
    requires: [
        'Ext.grid.ColumnLayout',
        'Ext.grid.plugin.HeaderResizer',
        'Ext.grid.plugin.HeaderReorderer'
    ],
    uses: [
        'Ext.grid.column.Column',
        'Ext.menu.Menu',
        'Ext.menu.CheckItem',
        'Ext.menu.Separator'
    ],
    border: true,

    alias: 'widget.headercontainer',

    baseCls: Ext.baseCSSPrefix + 'grid-header-ct',
    dock: 'top',

    /**
     * @cfg {Number} weight
     * HeaderContainer overrides the default weight of 0 for all docked items to 100.
     * This is so that it has more priority over things like toolbars.
     */
    weight: 100,

    defaultType: 'gridcolumn',

    detachOnRemove: false,

    /**
     * @cfg {Number} defaultWidth
     * Width of the header if no width or flex is specified.
     */
    defaultWidth: 100,

    /**
     * @cfg {Boolean} [sealed=false]
     * Specify as `true` to constrain column dragging so that a column cannot be dragged into or out of this column.
     *
     * **Note that this config is only valid for column headers which contain child column headers, eg:**
     *     {
     *         sealed: true
     *         text: 'ExtJS',
     *         columns: [{
     *             text: '3.0.4',
     *             dataIndex: 'ext304'
     *         }, {
     *             text: '4.1.0',
     *             dataIndex: 'ext410'
     *         }
     *     }
     *
     */

    //<locale>
    sortAscText: 'Sort Ascending',
    //</locale>
    //<locale>
    sortDescText: 'Sort Descending',
    //</locale>
    //<locale>
    sortClearText: 'Clear Sort',
    //</locale>
    //<locale>
    columnsText: 'Columns',
    //</locale>

    dirtyCls: Ext.baseCSSPrefix + 'grid-dirty-cell',

    headerOpenCls: Ext.baseCSSPrefix + 'column-header-open',

    menuSortAscCls: Ext.baseCSSPrefix + 'hmenu-sort-asc',

    menuSortDescCls: Ext.baseCSSPrefix + 'hmenu-sort-desc',

    menuColsIcon: Ext.baseCSSPrefix + 'cols-icon',

    // private; will probably be removed by 4.0
    triStateSort: false,

    ddLock: false,

    dragging: false,

    /**
     * @property {Boolean} isGroupHeader
     * True if this HeaderContainer is in fact a group header which contains sub headers.
     */

    /**
     * @cfg {Boolean} sortable
     * Provides the default sortable state for all Headers within this HeaderContainer.
     * Also turns on or off the menus in the HeaderContainer. Note that the menu is
     * shared across every header and therefore turning it off will remove the menu
     * items for every header.
     */
    sortable: true,

    /**
     * @cfg {Boolean} [enableColumnHide=true]
     * False to disable column hiding within this grid.
     */
    enableColumnHide: true,

    initComponent: function() {
        var me = this;

        me.headerCounter = 0;
        me.plugins = me.plugins || [];

        // TODO: Pass in configurations to turn on/off dynamic
        //       resizing and disable resizing all together

        // Only set up a Resizer and Reorderer for the topmost HeaderContainer.
        // Nested Group Headers are themselves HeaderContainers
        if (!me.isHeader) {
            if (me.enableColumnResize) {
                me.resizer = new Ext.grid.plugin.HeaderResizer();
                me.plugins.push(me.resizer);
            }
            if (me.enableColumnMove) {
                me.reorderer = new Ext.grid.plugin.HeaderReorderer();
                me.plugins.push(me.reorderer);
            }
        }

        // Base headers do not need a box layout
        if (me.isHeader && !me.items) {
            me.layout = me.layout || 'container';
        }
        // HeaderContainer and Group header needs a gridcolumn layout.
        else {
            me.layout = Ext.apply({
                type: 'gridcolumn',
                align: 'stretchmax'
            }, me.initialConfig.layout);
        }
        me.defaults = me.defaults || {};
        Ext.applyIf(me.defaults, {
            triStateSort: me.triStateSort,
            sortable: me.sortable
        });

        me.menuTask = new Ext.util.DelayedTask(me.updateMenuDisabledState, me);
        me.callParent();
        me.addEvents(
            /**
             * @event columnresize
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             * @param {Number} width
             */
            'columnresize',

            /**
             * @event headerclick
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             * @param {Ext.EventObject} e
             * @param {HTMLElement} t
             */
            'headerclick',

            /**
             * @event headercontextmenu
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             * @param {Ext.EventObject} e
             * @param {HTMLElement} t
             */
            'headercontextmenu',

            /**
             * @event headertriggerclick
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             * @param {Ext.EventObject} e
             * @param {HTMLElement} t
             */
            'headertriggerclick',

            /**
             * @event columnmove
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             * @param {Number} fromIdx
             * @param {Number} toIdx
             */
            'columnmove',
            /**
             * @event columnhide
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             */
            'columnhide',
            /**
             * @event columnshow
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             */
            'columnshow',
            /**
             * @event columnschanged
             * Fired after the columns change in any way, when a column has been hidden or shown, or when a column
             * is added to or removed from this header container.
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             */
            'columnschanged',
            /**
             * @event sortchange
             * @param {Ext.grid.header.Container} ct The grid's header Container which encapsulates all column headers.
             * @param {Ext.grid.column.Column} column The Column header Component which provides the column definition
             * @param {String} direction
             */
            'sortchange',
            /**
             * @event menucreate
             * Fired immediately after the column header menu is created.
             * @param {Ext.grid.header.Container} ct This instance
             * @param {Ext.menu.Menu} menu The Menu that was created
             */
            'menucreate'
        );
    },

    isLayoutRoot: function(){
        // Since we're docked, the width is always calculated
        // If we're hidden, the height is explicitly 0, which
        // means we'll be considered a layout root. However, we
        // still need the view to layout to update the underlying
        // table to match the size.
        if (this.hiddenHeaders) {
            return false;
        }
        return this.callParent();
    },

    onDestroy: function() {
        var me = this;

        me.menuTask.cancel();
        Ext.destroy(me.resizer, me.reorderer);
        me.callParent();
    },

    applyColumnsState: function(columns) {
        if (!columns || !columns.length) {
            return;
        }

        var me     = this,
            items  = me.items.items,
            count  = items.length,
            i      = 0,
            length = columns.length,
            c, col, columnState, index;

        for (c = 0; c < length; c++) {
            columnState = columns[c];

            for (index = count; index--; ) {
                col = items[index];
                if (col.getStateId && col.getStateId() == columnState.id) {
                    // If a column in the new grid matches up with a saved state...
                    // Ensure that the column is restored to the state order.
                    // i is incremented upon every column match, so all persistent
                    // columns are ordered before any new columns.
                    if (i !== index) {
                        me.moveHeader(index, i);
                    }

                    if (col.applyColumnState) {
                        col.applyColumnState(columnState);
                    }
                    ++i;
                    break;
                }
            }
        }
    },

    getColumnsState: function () {
        var me = this,
            columns = [],
            state;

        me.items.each(function (col) {
            state = col.getColumnState && col.getColumnState();
            if (state) {
                columns.push(state);
            }
        });

        return columns;
    },

    // Invalidate column cache on add
    // We cannot refresh the View on every add because this method is called
    // when the HeaderDropZone moves Headers around, that will also refresh the view
    onAdd: function(c) {
        var me = this;

        if (!c.headerId) {
            c.headerId = c.initialConfig.id || Ext.id(null, 'header-');
        }

        if (!c.stateId) {
            // This was the headerId generated in 4.0, so to preserve saved state, we now
            // assign a default stateId in that same manner. The stateId's of a column are
            // not global at the stateProvider, but are local to the grid state data. The
            // headerId should still follow our standard naming convention.
            c.stateId = c.initialConfig.id || ('h' + (++me.headerCounter));
        }

        //<debug warn>
        if (Ext.global.console && Ext.global.console.warn) {
            if (!me._usedIDs) {
                me._usedIDs = {};
            }
            if (me._usedIDs[c.headerId]) {
                Ext.global.console.warn(this.$className, 'attempted to reuse an existing id', c.headerId);
            }
            me._usedIDs[c.headerId] = true;
        }
        //</debug>
        me.callParent(arguments);
        me.onColumnAddRemove();
    },

    onColumnAddRemove: function() {
        var topHeaderCt,
            c = this;

        if (c.rendered) {
            c = this;
            // Upon add/remove of any column we need to purge the *HeaderContainer's* cache of leaf view columns.
            while (c instanceof Ext.grid.header.Container) {
                topHeaderCt = c;
                c = c.ownerCt;
            }
            topHeaderCt.purgeCache();
            topHeaderCt.fireEvent('columnschanged', topHeaderCt);
        }
    },

    // Invalidate column cache on remove
    // We cannot refresh the View on every remove because this method is called
    // when the HeaderDropZone moves Headers around, that will also refresh the view
    onRemove: function(c) {
        var me = this,
            ownerCt = me.ownerCt;

        me.callParent(arguments);

        //<debug warn>
        if (!me._usedIDs) {
            me._usedIDs = {};
        }
        delete me._usedIDs[c.headerId];
        //</debug>

        me.onColumnAddRemove();
        if (me.isGroupHeader && !me.items.getCount() && ownerCt) {
            // If we don't have any items left and we're a group, remove ourselves.
            // This will cascade up if necessary
            Ext.suspendLayouts();
            ownerCt.remove(me);
            Ext.resumeLayouts(true);
        }
    },

    // @private
    applyDefaults: function(config) {
        var ret;
        /*
         * Ensure header.Container defaults don't get applied to a RowNumberer
         * if an xtype is supplied. This isn't an ideal solution however it's
         * much more likely that a RowNumberer with no options will be created,
         * wanting to use the defaults specified on the class as opposed to
         * those setup on the Container.
         */
        if (config && !config.isComponent && config.xtype == 'rownumberer') {
            ret = config;
        } else {
            ret = this.callParent(arguments);

            // Apply default width unless it's a group header (in which case it must be left to shrinkwrap), or it's flexed
            if (!config.isGroupHeader && !('width' in ret) && !ret.flex) {
                ret.width = this.defaultWidth;
            }
        }
        return ret;
    },

    afterRender: function() {
        this.callParent();
        this.setSortState(undefined, true);

    },

    setSortState: function(){
        var store   = this.up('[store]').store,
            // grab the first sorter, since there may also be groupers
            // in this collection
            first = store.getFirstSorter(),
            hd;

        if (first) {
            hd = this.down('gridcolumn[dataIndex=' + first.property  +']');
            if (hd) {
                hd.setSortState(first.direction, false, true);
            }
        } else {
            this.clearOtherSortStates(null);
        }
    },

    getHeaderMenu: function(){
        var menu = this.getMenu(),
            item;

        if (menu) {
            item = menu.child('#columnItem');
            if (item) {
                return item.menu;
            }
        }
        return null;
    },

    onHeaderVisibilityChange: function(header, visible){
        var me = this,
            menu = me.getHeaderMenu(),
            item;

        if (menu) {
            // If the header was hidden programmatically, sync the Menu state
            item = me.getMenuItemForHeader(menu, header);
            if (item) {
                item.setChecked(visible, true);
            }
            // delay this since the headers may fire a number of times if we're hiding/showing groups
            if (menu.isVisible()) {
                me.menuTask.delay(50);
            }
        }
    },

    updateMenuDisabledState: function(menu) {
        var me = this,
            columns = me.query(':not([hidden])'),
            i,
            len = columns.length,
            item,
            checkItem,
            method;

        // If called from menu creation, it will be passed to avoid infinite recursion
        if (!menu) {
            menu = me.getMenu();
        }

        for (i = 0; i < len; ++i) {
            item = columns[i];
            checkItem = me.getMenuItemForHeader(menu, item);
            if (checkItem) {
                method = item.isHideable() ? 'enable' : 'disable';
                if (checkItem.menu) {
                    method += 'CheckChange';
                }
                checkItem[method]();
            }
        }
    },

    getMenuItemForHeader: function(menu, header) {
        return header ? menu.down('menucheckitem[headerId=' + header.id + ']') : null;
    },

    onHeaderShow: function(header) {
        // Pass up to the GridSection
        var me = this,
            gridSection = me.ownerCt;

        if (me.forceFit) {
            delete me.flex;

        }

        me.onHeaderVisibilityChange(header, true);

        // Only update the grid UI when we are notified about base level Header shows;
        // Group header shows just cause a layout of the HeaderContainer
        if (!header.isGroupHeader) {
            if (gridSection) {
                gridSection.onHeaderShow(me, header);
            }
        }
        me.fireEvent('columnshow', me, header);
        me.fireEvent('columnschanged', this);
    },

    onHeaderHide: function(header) {
        // Pass up to the GridSection
        var me = this,
            gridSection = me.ownerCt;

        me.onHeaderVisibilityChange(header, false);

        // Only update the UI when we are notified about base level Header hides;
        if (!header.isGroupHeader) {
            if (gridSection) {
                gridSection.onHeaderHide(me, header);
            }
        }
        me.fireEvent('columnhide', me, header);
        me.fireEvent('columnschanged', this);
    },

    /**
     * Temporarily lock the headerCt. This makes it so that clicking on headers
     * don't trigger actions like sorting or opening of the header menu. This is
     * done because extraneous events may be fired on the headers after interacting
     * with a drag drop operation.
     * @private
     */
    tempLock: function() {
        this.ddLock = true;
        Ext.Function.defer(function() {
            this.ddLock = false;
        }, 200, this);
    },

    onHeaderResize: function(header, w, suppressFocus) {
        var me = this,
            view = me.view,
            gridSection = me.ownerCt;

        // Do not react to header sizing during initial Panel layout when there is no view content to size.
        if (view && view.body.dom) {
            me.tempLock();
            if (gridSection) {
                gridSection.onHeaderResize(me, header, w);
            }
        }
        me.fireEvent('columnresize', this, header, w);
    },

    onHeaderClick: function(header, e, t) {
        header.fireEvent('headerclick', this, header, e, t);
        this.fireEvent('headerclick', this, header, e, t);
    },

    onHeaderContextMenu: function(header, e, t) {
        header.fireEvent('headercontextmenu', this, header, e, t);
        this.fireEvent('headercontextmenu', this, header, e, t);
    },

    onHeaderTriggerClick: function(header, e, t) {
        // generate and cache menu, provide ability to cancel/etc
        var me = this;
        if (header.fireEvent('headertriggerclick', me, header, e, t) !== false && me.fireEvent('headertriggerclick', me, header, e, t) !== false) {
            me.showMenuBy(t, header);
        }
    },

    showMenuBy: function(t, header) {
        var menu = this.getMenu(),
            ascItem  = menu.down('#ascItem'),
            descItem = menu.down('#descItem'),
            sortableMth;

        menu.activeHeader = menu.ownerCt = header;
        menu.setFloatParent(header);
        header.setMenuActive(true);

        // enable or disable asc & desc menu items based on header being sortable
        sortableMth = header.sortable ? 'enable' : 'disable';
        if (ascItem) {
            ascItem[sortableMth]();
        }
        if (descItem) {
            descItem[sortableMth]();
        }
        menu.showBy(t);
    },

    // remove the trigger open class when the menu is hidden
    onMenuDeactivate: function(menu) {
        menu.activeHeader.setMenuActive(false);
    },

    moveHeader: function(fromIdx, toIdx) {
        // An automatically expiring lock
        this.tempLock();
        this.onHeaderMoved(this.move(fromIdx, toIdx), 1, fromIdx, toIdx);
    },

    purgeCache: function() {
        var me = this;
        // Delete column cache - column order has changed.
        delete me.gridDataColumns;
        delete me.hideableColumns;

        // Menu changes when columns are moved. It will be recreated.
        if (me.menu) {
            // Must hide before destroy so that trigger el is deactivated
            me.menu.hide();
            me.menu.destroy();
            me.menu = null;
        }
    },

    onHeaderMoved: function(header, colsToMove, fromIdx, toIdx) {
        var me = this,
            gridSection = me.ownerCt;

        if (gridSection && gridSection.onHeaderMove) {
            gridSection.onHeaderMove(me, header, colsToMove, fromIdx, toIdx);
        }
        me.fireEvent("columnmove", me, header, fromIdx, toIdx);
    },

    /**
     * Gets the menu (and will create it if it doesn't already exist)
     * @private
     */
    getMenu: function() {
        var me = this;

        if (!me.menu) {
            me.menu = new Ext.menu.Menu({
                hideOnParentHide: false,  // Persists when owning ColumnHeader is hidden
                items: me.getMenuItems(),
                listeners: {
                    deactivate: me.onMenuDeactivate,
                    scope: me
                }
            });
            me.fireEvent('menucreate', me, me.menu);
        }
        me.updateMenuDisabledState(me.menu);
        return me.menu;
    },

    /**
     * Returns an array of menu items to be placed into the shared menu
     * across all headers in this header container.
     * @returns {Array} menuItems
     */
    getMenuItems: function() {
        var me = this,
            menuItems = [],
            hideableColumns = me.enableColumnHide ? me.getColumnMenu(me) : null;

        if (me.sortable) {
            menuItems = [{
                itemId: 'ascItem',
                text: me.sortAscText,
                cls: me.menuSortAscCls,
                handler: me.onSortAscClick,
                scope: me
            },{
                itemId: 'descItem',
                text: me.sortDescText,
                cls: me.menuSortDescCls,
                handler: me.onSortDescClick,
                scope: me
            }];
        }
        if (hideableColumns && hideableColumns.length) {
            menuItems.push('-', {
                itemId: 'columnItem',
                text: me.columnsText,
                cls: me.menuColsIcon,
                menu: hideableColumns,
                hideOnClick: false
            });
        }
        return menuItems;
    },

    // sort asc when clicking on item in menu
    onSortAscClick: function() {
        var menu = this.getMenu(),
            activeHeader = menu.activeHeader;

        activeHeader.setSortState('ASC');
    },

    // sort desc when clicking on item in menu
    onSortDescClick: function() {
        var menu = this.getMenu(),
            activeHeader = menu.activeHeader;

        activeHeader.setSortState('DESC');
    },

    /**
     * Returns an array of menu CheckItems corresponding to all immediate children
     * of the passed Container which have been configured as hideable.
     */
    getColumnMenu: function(headerContainer) {
        var menuItems = [],
            i = 0,
            item,
            items = headerContainer.query('>gridcolumn[hideable]'),
            itemsLn = items.length,
            menuItem;

        for (; i < itemsLn; i++) {
            item = items[i];
            menuItem = new Ext.menu.CheckItem({
                text: item.menuText || item.text,
                checked: !item.hidden,
                hideOnClick: false,
                headerId: item.id,
                menu: item.isGroupHeader ? this.getColumnMenu(item) : undefined,
                checkHandler: this.onColumnCheckChange,
                scope: this
            });
            menuItems.push(menuItem);

            // If the header is ever destroyed - for instance by dragging out the last remaining sub header,
            // then the associated menu item must also be destroyed.
            item.on({
                destroy: Ext.Function.bind(menuItem.destroy, menuItem)
            });
        }
        return menuItems;
    },

    onColumnCheckChange: function(checkItem, checked) {
        var header = Ext.getCmp(checkItem.headerId);
        header[checked ? 'show' : 'hide']();
    },

    /**
     * Returns the number of <b>grid columns</b> descended from this HeaderContainer.
     * Group Columns are HeaderContainers. All grid columns are returned, including hidden ones.
     */
    getColumnCount: function() {
        return this.getGridColumns().length;
    },

    /**
     * Gets the full width of all columns that are visible.
     */
    getFullWidth: function(flushCache) {
        var fullWidth = 0,
            headers = this.getVisibleGridColumns(flushCache),
            headersLn = headers.length,
            i = 0,
            header;
           

        for (; i < headersLn; i++) {
            header = headers[i];
            // use headers getDesiredWidth if its there
            if (header.getDesiredWidth) {
                fullWidth += header.getDesiredWidth() || 0;
            // if injected a diff cmp use getWidth
            } else {
                fullWidth += header.getWidth();
            }
        }
        return fullWidth;
    },

    // invoked internally by a header when not using triStateSorting
    clearOtherSortStates: function(activeHeader) {
        var headers   = this.getGridColumns(),
            headersLn = headers.length,
            i         = 0;

        for (; i < headersLn; i++) {
            if (headers[i] !== activeHeader) {
                // unset the sortstate and dont recurse
                headers[i].setSortState(null, true);
            }
        }
    },

    /**
     * Returns an array of the **visible** columns in the grid. This goes down to the lowest column header
     * level, and does not return **grouped** headers which contain sub headers.
     * @param {Boolean} refreshCache If omitted, the cached set of columns will be returned. Pass true to refresh the cache.
     * @returns {Array}
     */
    getVisibleGridColumns: function(refreshCache) {
        return Ext.ComponentQuery.query(':not([hidden])', this.getGridColumns(refreshCache));
    }, 

    /**
     * Returns an array of all columns which appear in the grid's View. This goes down to the leaf column header
     * level, and does not return **grouped** headers which contain sub headers.
     *
     * It includes hidden headers because they are still rendered into a table view, but with zero width.
     *
     * Headers which have a hidden ancestor have a `hiddenAncestor: true` property injected so that they can also be rendered at zero width without interrogating
     * that header's ownerCt axis for a hidden ancestor.
     *
     * @param {Boolean} refreshCache If omitted, the cached set of columns will be returned. Pass true to refresh the cache.
     * @returns {Array}
     */
    getGridColumns: function(refreshCache /* private - used in recursion*/, inResult, hiddenAncestor) {
        if (!refreshCache && this.gridDataColumns) {
            return this.gridDataColumns;
        }

        var me = this,
            result = inResult || [],
            items, i, len, item,
            lastVisibleColumn;

        hiddenAncestor = hiddenAncestor || me.hidden;
        if (me.items) {
            items = me.items.items;
            for (i = 0, len = items.length; i < len; i++) {
                item = items[i];
                if (item.isGroupHeader) {
                    item.getGridColumns(true, result, hiddenAncestor);
                } else {
                    item.hiddenAncestor = hiddenAncestor;
                    result.push(item);
                }
            }
        }
        me.gridDataColumns = result;

        // If top level, correct first and last visible column flags
        if (!inResult && len) {
            // Set firstVisible and lastVisible flags
            for (i = 0, len = result.length; i < len; i++) {
                item = result[i];
                item.isFirstVisible = item.isLastVisible = false;
                if (!item.hidden || item.hiddenAncestor) {
                    if (!lastVisibleColumn) {
                        item.isFirstVisible = true;
                    }
                    lastVisibleColumn = item;
                }
            }
            lastVisibleColumn.isLastVisible = true;
        }

        return result;
    },

    /**
     * @private
     * For use by column headers in determining whether there are any hideable columns when deciding whether or not
     * the header menu should be disabled.
     */
    getHideableColumns: function(refreshCache) {
        var me = this,
            result = refreshCache ? null : me.hideableColumns;

        if (!result) {
            result = me.hideableColumns = me.query('[hideable]');
        }
        return result;
    },

    /**
     * Returns the index of a leaf level header regardless of what the nesting
     * structure is.
     *
     * If a group header is passed, the index of the first leaf level heder within it is returned.
     *
     * @param {Ext.grid.column.Column} header The header to find the index of
     * @return {Number} The index of the specified column header
     */
    getHeaderIndex: function(header) {
        // If we are being asked the index of a group header, find the first leaf header node, and return the index of that
        if (header.isGroupHeader) {
            header = header.down(':not([isGroupHeader])');
        }
        return Ext.Array.indexOf(this.getGridColumns(), header);
    },

    /**
     * Get a leaf level header by index regardless of what the nesting
     * structure is.
     * @param {Number} The column index for which to retrieve the column.
     */
    getHeaderAtIndex: function(index) {
        var columns = this.getGridColumns();
        return columns.length ? columns[index] : null;
    },

    /**
     * When passed a column index, returns the closet *visible* column to that. If the column at the passed index is visible,
     * that is returned. If it is hidden, either the next visible, or the previous visible column is returned.
     * @param {Number} index Position at which to find the closest visible column.
     */
    getVisibleHeaderClosestToIndex: function(index) {
        var result = this.getHeaderAtIndex(index);
        if (result && result.hidden) {
            result = result.next(':not([hidden])') || result.prev(':not([hidden])');
        }
        return result;
    },

    autoSizeColumn : function(header) {
        var view = this.view;
        if (view) {
            view.autoSizeColumn(header);
        }
    }
});
