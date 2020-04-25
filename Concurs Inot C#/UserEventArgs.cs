using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.client
{
    public enum UserEvent
    {
        UPDATE
    }
    public class UserEventArgs : EventArgs
    {
        private readonly UserEvent userEvent;
        private readonly Object data;
        private readonly Object data2;
        public UserEventArgs(UserEvent userEvent, object data, object data2)
        {
            this.userEvent = userEvent;
            this.data = data;
            this.data2 = data2;

        }
        public UserEvent GetUserEventType()
        {
            return userEvent;
        }
        public object GetData()
        {
            return data;
        }

        public object GetData2()
        {
            return data2;
        }
    }
}
