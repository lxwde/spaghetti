// Modules to control application life and create native browser window
const {app, BrowserWindow, Menu} = require('electron')
const contextMenu = require('electron-context-menu')
const path = require('path')
const isMac = process.platform === 'darwin'


const template = [
  // { role: 'appMenu' }
  ...(isMac ? [{
    label: app.name,
    submenu: [
      { role: 'about', label: "关于" },
      { type: 'separator' },
      { role: 'services', label: "服务" },
      { type: 'separator' },
      { role: 'hide', label: "隐藏应用程序" },
      { role: 'hideOthers', label: "隐藏其它应用程序" },
      { role: 'unhide', label: "取消隐藏其他应用程序" },
      { type: 'separator' },
      { role: 'quit', label: "退出" }
    ]
  }] : []),
  // { role: 'fileMenu' }
  {
    label: '文件',
    submenu: [
      isMac ? { role: 'close', label: '关闭' } : { role: 'quit', label: '退出' }
    ]
  },
  // { role: 'editMenu' }
  {
    label: '编辑',
    submenu: [
      { role: 'undo', label: '撤销' },
      { role: 'redo', label: '重做' },
      { type: 'separator' },
      { role: 'cut', label: '剪切' },
      { role: 'copy', label: '拷贝' },
      { role: 'paste', label: '粘贴' },
      ...(isMac ? [
        { role: 'pasteAndMatchStyle', label: '粘贴并匹配样式' },
        { role: 'delete', label: '删除' },
        { role: 'selectAll', label: '全选' },
        { type: 'separator' },
        {
          label: 'Speech',
          submenu: [
            { role: 'startSpeaking', label: '开始说话' },
            { role: 'stopSpeaking', label: '停止说话' }
          ]
        }
      ] : [
        { role: 'delete', label: '删除' },
        { type: 'separator' },
        { role: 'selectAll', label: '全选' }
      ])
    ]
  },
  // { role: 'viewMenu' }
  {
    label: '视图',
    submenu: [
      { role: 'reload', label: '刷新' },
      { role: 'forceReload', label: '刷新(清空缓存)' },
      // { role: 'toggleDevTools' },
      { type: 'separator' },
      { role: 'resetZoom', label: "缩放重置" },
      { role: 'zoomIn', label: "放大" },
      { role: 'zoomOut', label: "缩小" },
      { type: 'separator' },
      { role: 'togglefullscreen', label: "切换全屏" }
    ]
  },
  // { role: 'windowMenu' }
  {
    label: '窗体',
    submenu: [
      { role: 'minimize', label: "最小化" },
      { role: 'zoom', label: "缩放" },
      ...(isMac ? [
        { type: 'separator' },
        { role: 'front', label: "前置" },
        { type: 'separator' },
        { role: 'window', label: "窗体" }
      ] : [
        { role: 'close', label: "关闭" }
      ])
    ]
  },
  {
    role: 'help',
    label: '帮助',
    submenu: [
      {
        label: '关于...',
        click: async () => {
          const { dialog } = require('electron')
          const options = {
            type: 'question',
            buttons: ['OK'],
            defaultId: 2,
            title: '关于',
            message: '云汇企业管理软件柯桥家具公司定制版',
            detail: ''
          };
        
          dialog.showMessageBox(BrowserWindow.getFocusedWindow(), options, (response, checkboxChecked) => {
            console.log(response);
            console.log(checkboxChecked);
          });
        }
      }
    ]
  }
]

const menu = Menu.buildFromTemplate(template)
Menu.setApplicationMenu(menu)

contextMenu({
  // showInspectElement: false,
  // showSearchWithGoogle: false,
  // shouldShowMenu: (event, parameters) => !parameters.isEditable,
  // labels: {
  //   copy: '拷贝',
  //   paste: '粘贴…',
  //   cut: '剪切'
  // },
  menu: (actions, props, browserWindow, dictionarySuggestions) => [
		...dictionarySuggestions,
		actions.separator(),
		// actions.copyLink({
		// 	transform: content => `modified_link_${content}`
		// }),

		// actions.separator(),
		actions.copy({
			// transform: content => `modified_copy_${content}`
		}),
		{
			label: 'Invisible',
			visible: false
		},
		actions.paste({
			// transform: content => `modified_paste_${content}`
		}),
    actions.cut({
			// transform: content => `modified_paste_${content}`
		}),
    actions.copyImage()
	]
});


function createWindow () {
  // Create the browser window.
  let mainWindow = null
  let loading = new BrowserWindow({show: false, frame: false, icon:__dirname + '/favicon.ico'})

  loading.once('show', () => {
    const mainWindow = new BrowserWindow({
      show: false,
      icon:__dirname + '/favicon.ico',
      webPreferences: {
        preload: path.join(__dirname, 'preload.js')
      }
    });

    // mainWindow.setMenuBarVisibility(false)
    mainWindow.webContents.once('dom-ready', () => {
      console.log('main loaded')
      mainWindow.show()
      mainWindow.maximize();
      mainWindow.setTitle("System");
     loading.hide()
     loading.close()
    })
    mainWindow.loadURL('http://139.196.31.59:8069/web');
    // mainWindow.loadURL('http://localhost:8013/web');
    mainWindow.webContents.on('did-fail-load', function (event, code, desc, url, isMainFrame) {
      console.log('DID FAIL LOAD: ', code, desc, url);
      
      // mainWindow.loadURL('file://' + __dirname + '/index.html')
      const { dialog } = require('electron')
      const options = {
        type: 'question',
        buttons: ['OK'],
        defaultId: 2,
        title: '加载程序失败',
        message: '检查网络设置后选择菜单-视图-刷新，或者重新启动程序',
        detail: ''
      };
    
      dialog.showMessageBox(mainWindow, options, (response, checkboxChecked) => {
        console.log(response);
        console.log(checkboxChecked);
      });
    });
  })
  loading.loadURL('file://' + __dirname + '/loading.html')
  loading.show()
  
  // Open the DevTools.
  // mainWindow.webContents.openDevTools()
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.whenReady().then(() => {
  createWindow();

  app.on('activate', function () {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (BrowserWindow.getAllWindows().length === 0) createWindow()
  });
})

// Quit when all windows are closed, except on macOS. There, it's common
// for applications and their menu bar to stay active until the user quits
// explicitly with Cmd + Q.
app.on('window-all-closed', function () {
  if (process.platform !== 'darwin') app.quit();
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.
